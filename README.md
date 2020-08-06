# ToDoApp
To Do is a Application which helps in managing tasks built with MVVM architecture using Androidx and Android Jetpack components.

Package-Structure:
This Project contains mainly four components :

1. DataSource - This package provides data source to the App using both Network and Room DB.

2. DI - This package contains Dagger v2.0 Modules and Components used to provide dependency at runtime.

3. Repository - This layer provides an interface between ViewModel and DataSource.

4. View/ViewModel - This layer provides interaction between View and ViewModel using Data Binding approach.

Dependencies:

1. Android Lifecycle: Android lifecycle components such as RoomDB and lifecycle extensions.
