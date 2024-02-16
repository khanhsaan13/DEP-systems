# DEP-systems
The DEP system helps a company to manage departments, employees, projects and developers work on projects. The system load data of departments, employees, projects, and works-on from the text files when the program starts. Then the application shows a Graphic User Interface (GUI) so that a user can choose what to do.

The program is required to be run in the terminal with JavaFX libraries installed locally. I am trying to find a way to somehow make it into a solid executable file. 

UML Class Diagrams
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/79a22241-1240-4825-9f2e-ad1a57e1969b)

Processing examples
When the application starts, it loads data and stores them into the containers, then shows lists of
departments, employees, projects, works-on and the pie chart of department budgets like the GUI below.
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/e3013300-a6fb-4cae-ade3-fe33752cd66d)

Processing examples
When the application starts, it loads data and stores them into the containers, then shows lists of
departments, employees, projects, works-on and the pie chart of department budgets like the GUI below.
If a user clicks an item of a list, the application displays the details of the item below the list.

![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/c0096e4f-1b4a-49d8-8243-0c62fbbe0bf9)

If a user clicks a button Add, the application validates the employee, project and works on information.
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/c7ae73dc-d059-49b3-8e30-fe79531cc626)
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/8e6257f0-6158-4035-a869-9ee370058c29)

If a developer, project and works-on are valid, the application pops a dialog to get hours, and add the new
works-on to the list and the container workson.
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/2f4b1eae-18ec-454f-aef6-9708109d2802)
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/c3f02a6a-d914-4e3b-bc00-cdd46a186df3)

If a user clicks a button Delete, the application pops a dialog to confirm the deletion, then delete the
selected works-on data from both container workson and the list of works-on.
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/9bbb0138-b298-4621-b07d-1cfa4cb4704e)
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/3dc98f79-effc-487a-9c30-8d34c9013d1a)

If a user clicks a button Save, the application saves the works-on records into a file workson.txt and
display message such as 7 workson records are saved.
![image](https://github.com/khanhsaan13/DEP-systems/assets/129593167/c64acd3a-90ad-40db-bccd-07cde53944ea)










