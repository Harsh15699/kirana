Assumptions
===========================
There is a single user for this scenario, and they can add transactions in either INR or USD.

If the user performs a transaction in INR, it will be stored in the database as is.

If the user chooses USD, the amount will be converted to INR, and the response will reflect the conversion.

Currency exchange rates are relatively unstable, and there's need to fetch them periodically.

Covered Scenarios
===========================
User can add a new transaction.

User can fetch all transactions.

User can fetch transactions for a specific day.

Notes
===========================
Storing both INR and USD transactions separately is not considered optimal.

Sending data grouped by date is avoided. Instead, the API allows fetching data for a specific date when needed.

UNIT Test Coverage
===========================

I have covered few scenarios for unit testing for both controller and service.

API Documentation
===========================
https://1drv.ms/w/c/3d24fc64f65ff74d/EXS9xauc7gVMiF79mI21fGkBDpnKQf42x27ODmMNz91F8g?e=BCUylo




