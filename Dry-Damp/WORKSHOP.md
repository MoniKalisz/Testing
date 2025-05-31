DRY vs DAMP

Tasks by pair:

Team 1 and Team 2: REFACTORING FROM DAMP TO DRY:

Rewrite the tests using the DRY approach:
1. identify recurring patterns in the setup
2. extract auxiliary methods for common logic
3. keep the tests readable - each should continue to tell a clear story
4. avoid excessive abstraction - helper should be simple and understandable
5. helper names should reflect the business intent

Team 3 and Team 4: REFACTORING FROM DRY TO DAMP:

Rewrite the tests using the DAMP approach:
1. remove excessive abstraction in the helpers
2. each test should clearly show the business case
3. use Given-When-Then with descriptive comments
4. test names should describe specific business rules
5. code duplication is OK if it increases readability