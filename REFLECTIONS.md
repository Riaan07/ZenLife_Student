# Zenlife Architecture Verification Sheet: Reflections Assignment

## Module A: Interface Binding & View Recycling (Chapters 9, 10, & 11)

### Question 1
Inside `LogAdapter.onCreateViewHolder()`, explain your layout inflation arguments.
If a developer replaces your exact configuration by passing 'true' as the final
boolean parameter (attachToRoot), what specific error occurs at runtime, and why
does the RecyclerView infrastructure prohibit it?

My onCreateViewHolder calls inflate(R.layout.list_item_log, parent, false).
parent is passed so the layout’s width, height and margins are resolved against the RecyclerView.
false is used because the RecyclerView attaches each item view itself, when it needs it.
With true, inflate attaches the item to the RecyclerView straight away and returns the RecyclerView, 
not the item. RecyclerView then throws an IllegalStateException saying ViewHolder views must not be 
attached when created, and not to pass true for attachToRoot.

### Question 2
In `LogViewHolder.bind()`, you implemented click actions that toggle card strings.
Why must you explicitly force state variable values (isShowingDetails = false) and
re-apply baseline layouts at the start of the bind process before handling click triggers?
Detail what specific visual glitch happens to scrolling items if this step is omitted.

RecyclerView reuses a small number of ViewHolders as you scroll.
isShowingDetails and the displayed text belong to the ViewHolder, not to the data.
Without the reset, an expanded card that scrolls off the screen hands its expanded state to the next row that reuses it.
On screen you’d see random cards already expanded, or text that doesn’t match the flag, so a tap seems to do nothing the first time.
---

## Module B: Persistent Engines & Data Pipelines (Chapters 12 & 13)

### Question 3
Your Room Dao queries return data mapped directly into a cold Kotlin 'Flow'.
What specific advantage does exposing data via 'Flow' provide for your UI over a
standard flat data 'List', and how does the ViewModel transform this data stream safely
to make it lifecycle-aware?

Room’s Flow emits the current list and then emits again every time the table changes.
A plain List is a one-time snapshot, so you’d have to re-query after every insert or delete.
In ZenLifeViewModel, dao.getAllLogs().asLiveData() turns the Flow into LiveData.
LiveData only delivers to observers in an active lifecycle state, and the Flow collection starts and stops with them.
The Dashboard observes it with viewLifecycleOwner, so nothing updates a destroyed view.

### Question 4
Your ViewModel wraps data operations inside a looping task chain. If a student deletes
this threading setup and executes the raw internal database commands directly within
a button click context listener, what specific runtime crash is thrown by the Android OS,
and what design principle does this protect?

If you run database work directly in a click listener, Room throws an IllegalStateException: 
“Cannot access database on the main thread since it may potentially lock the UI for a long period of time.”
Strictly, Room throws it and not the OS.
My insertLog and clearAllLogs are suspend functions, so calling them outside a coroutine wouldn’t even compile.
It protects the rule that the main thread must stay responsive. Slow disk work on it causes jank, and after about 
5 seconds an “Application Not Responding” dialog.
My viewModelScope.launch moves the work off the main thread and cancels it when the ViewModel is cleared.
---

## Module C: Dialog Management & Component Scopes (Chapters 14 & 15)

### Question 5
When navigating to 'ConfirmDeleteDialog', your code shares the data engine state
by structuring its declaration hook via 'by viewModels({ requireActivity() })'
instead of a default standalone view-model lookup line. What structural issue does this
resolve, and what would happen if the dialog generated its own independent view-model instance?

The default viewModels() in a DialogFragment ties the ViewModel to the dialog itself, so it is a 
separate instance with its own state.
It is destroyed when the dialog closes, which cancels its viewModelScope right after clearLogs() starts 
and may cut off the delete.
Scoping to the activity gives a ViewModel that lives as long as the host, so it isn’t cancelled by dismiss() 
and is the same instance other activity-scoped components use.
In this skeleton DashboardFragment uses plain viewModels(), which is fragment-scoped, 
and I can’t change the skeleton. The list still refreshes after a 
delete because both ViewModels use the same Room database singleton and the Flow re-emits.