Hi :)

Database and API service have been moved to separate modules.
They could have been placed in the data package, but I decided that, as an introduction to modularization, 
it would be more appropriate to move them to a separate module.
The domain layer is absent here, except for the models, as I do not see any business logic in this project that would justify creating UseCases.