# Zenlife Architecture Verification Sheet: Reflections Assignment

## Module A: Interface Binding & View Recycling (Chapters 9, 10, & 11)

### Question 1
Inside `LogAdapter.onCreateViewHolder()`, explain your layout inflation arguments.
If a developer replaces your exact configuration by passing 'true' as the final
boolean parameter (attachToRoot), what specific error occurs at runtime, and why
does the RecyclerView infrastructure prohibit it?

### Question 2
In `LogViewHolder.bind()`, you implemented click actions that toggle card strings.
Why must you explicitly force state variable values (isShowingDetails = false) and
re-apply baseline layouts at the start of the bind process before handling click triggers?
Detail what specific visual glitch happens to scrolling items if this step is omitted.

---

## Module B: Persistent Engines & Data Pipelines (Chapters 12 & 13)

### Question 3
Your Room Dao queries return data mapped directly into a cold Kotlin 'Flow'.
What specific advantage does exposing data via 'Flow' provide for your UI over a
standard flat data 'List', and how does the ViewModel transform this data stream safely
to make it lifecycle-aware?

### Question 4
Your ViewModel wraps data operations inside a looping task chain. If a student deletes
this threading setup and executes the raw internal database commands directly within
a button click context listener, what specific runtime crash is thrown by the Android OS,
and what design principle does this protect?

---

## Module C: Dialog Management & Component Scopes (Chapters 14 & 15)

### Question 5
When navigating to 'ConfirmDeleteDialog', your code shares the data engine state
by structuring its declaration hook via 'by viewModels({ requireActivity() })'
instead of a default standalone view-model lookup line. What structural issue does this
resolve, and what would happen if the dialog generated its own independent view-model instance?
