Task 1: Handling the resubmission of an application after rejection

> “If a request has been rejected, the user can resubmit it for analysis.”

1 Add `resubmit()` method in `LoanProcessService`.
2. enable state transition: `REJECTED -> UNDER_REVIEW`.
3. write a state-based test: whether the state changes correctly
4. write interaction-based test: does NOT resend notification

---

Task 2: Negative tests - illegal transitions

> “Do not withdraw funds if the application has not been approved.”

1. write a test that attempts to execute `disburse()` from the `NEW` or `REJECTED` state.
2. verify that the state does not change (or throw an exception)
3. consider: should there be an error logging? exception? or silent denial?

---

Task 3 (for those willing): Event Sourcing Simulation

> “Add event history to a loan application.”

1. in `LoanApplication` add a `List<String>` or `List<LoanEvent>` - event list.
2. each state transition adds an entry to this list
3. write tests that validate the history