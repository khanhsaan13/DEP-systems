/*------------------------------------------------------
My name: Quoc Van Khanh An Tran (Anthony)
My student number: 6778136
My course code: CSIT213
My email address: qvkat302@uowmail.edu.au
Assignment number: 3
-------------------------------------------------------*/

import java.util.*;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.chart.PieChart;

public class DEP extends Application implements EventHandler<ActionEvent>{
    private ArrayList<Department> departments;
    private TextField deName, deManager, deBudget, deStartDate;

    private ArrayList<Employee> employees;
    private Employee currentEmployee;
    private ArrayList<Admin> admins;
    private ArrayList<Developer> devs;
    private TextField eName, eDOB, eAddress, eGender, eSalary, eSupervisor, eDepartment, eSkillLanguage;

    private ArrayList<Project> projects;
    private Project currentProject;
    private TextField proTitle, proSponsor, proDepartment, proBudget;

    private ArrayList<WorksOn> workson;
    private WorksOn currentWorksOn;
    private TextField wEmployeeNumber, wProjectNumber, wHours;

    private GridPane departmentPane, employeesPane, projectPane, worksonPane, chartPane;
    private ListView<Integer>depList, emList, proList;
    private ListView<String> workList;
    private Button add, delete, save;
    private FlowPane buttonPane;
    private TextField message;
    private GridPane mainPane;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void init(){
        departments = new ArrayList<Department>();
        employees = new ArrayList<Employee>();
        admins = new ArrayList<Admin>();
        devs = new ArrayList<Developer>();
        projects = new ArrayList<Project>();
        workson = new ArrayList<WorksOn>();

        loadDepartments();
        loadEmployees();
        loadProjects();
        loadWorksOn();
    }

    @Override
    public void start(Stage ps){
        //Create the root
        BorderPane root = new BorderPane();

        // set department pane
        setDepartmentPane();
        // root.setLeft(departmentPane);

        // set employee pane
        setEmployeePane();

        // set project pane
        setProjectPane();

        // set button pane
        setButtonPane();

        // set works on pane
        setWorksOnPane();

        setChartPane();

        // set main pane
        setMain();
        root.setCenter(mainPane);

        Scene scene = new Scene(root, 1300, 900);

        // Prepare the stage
        ps.setScene(scene);
        ps.setTitle("Departments, employees nad projects management system");
        ps.show();
    }

    public void setMain(){
        mainPane = new GridPane();
        mainPane.setStyle("--fx-background-color: white;");
        mainPane.setVgap(10);
        mainPane.setHgap(10);

        mainPane.addColumn(0, departmentPane);
        mainPane.addColumn(1, employeesPane);
        mainPane.addColumn(2, projectPane);
        mainPane.addColumn(3, worksonPane);

        mainPane.setColumnSpan(chartPane, 4);
        mainPane.add(chartPane, 0, 1);
        
        mainPane.setAlignment(Pos.CENTER);
    }

    public void setChartPane(){
        chartPane = new GridPane();
        // create a pie chart
        PieChart pc = new PieChart();
        for(Department d: departments){
            String name = String.format("%d %s", d.getNumber(), d.getName());
            pc.getData().add(new PieChart.Data(name, d.getBudget()));
        }
        chartPane.addRow(0, pc);

        chartPane.setAlignment(Pos.CENTER);
    }

    public void setButtonPane(){
        buttonPane = new FlowPane();
        buttonPane.setStyle("-fx-background-color: white;");
        buttonPane.setHgap(10);

        // add Buttons
        add = new Button("Add");
        add.setOnAction(this);
        buttonPane.getChildren().add(add);

        delete = new Button("Delete");
        delete.setOnAction(this);
        buttonPane.getChildren().add(delete);

        save = new Button("Save");
        save.setOnAction(this);
        buttonPane.getChildren().add(save);

        buttonPane.setAlignment(Pos.CENTER);
    }

    public void setWorksOnPane(){
        worksonPane = new GridPane();
        worksonPane.setStyle("-fx-background-color: white;");
        worksonPane.setVgap(10);
        worksonPane.setHgap(30);

        // add the works on list
        workList = new ListView<String>();
        workList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue v, String oldV, String newV){
                WorksOn work = getWorksOn(newV);
                // pass all the values to the current works on
                currentWorksOn = work;
                if(work != null){
                    wEmployeeNumber.setText(String.format("%d", work.getENumber()));
                    wProjectNumber.setText(String.format("%d", work.getPNumber()));
                    wHours.setText(String.format("%d", work.getHours()));
                }
                else{
                    message.setText(String.format("Cannot find the work %d", newV));
                }
            }
        });
        
        // add the work details
        worksonPane.addRow(0, new Label("     Works on"));
        worksonPane.addRow(0, new Label("information"));

        // set the work list
        for(WorksOn w: workson){
            workList.getItems().add(w.toString());
        }

        // add the work list to the scroll pane
        ScrollPane sp = new ScrollPane();
        sp.setPrefViewportHeight(100);
        sp.setPrefViewportWidth(400);
        sp.setContent(workList);

        worksonPane.addRow(1, new Label("Works on"));
        worksonPane.addRow(1, sp);

        // add emloyee number
        worksonPane.addRow(2, new Label("Employee\nnumber"));
        wEmployeeNumber = new TextField();
        wEmployeeNumber.setEditable(false);
        worksonPane.addRow(2, wEmployeeNumber);

        // add project number
        worksonPane.addRow(3, new Label("Project\nnumber"));
        wProjectNumber = new TextField();
        wProjectNumber.setEditable(false);
        worksonPane.addRow(3, wProjectNumber);

        // add hours
        worksonPane.addRow(4, new Label("Hours"));
        wHours = new TextField();
        wHours.setEditable(false);
        worksonPane.addRow(4, wHours);

        // add button
        worksonPane.addRow(5, new Label(""));
        worksonPane.addRow(5, buttonPane);

        // add Message
        worksonPane.addRow(6, new Label("Message"));
        message = new TextField();
        message.setEditable(false);
        worksonPane.addRow(6, message);
    }

    public WorksOn getWorksOn(String s){
        for(WorksOn w: workson){
            if(s.equals(w.toString())){
                return w;
            }
        }
        return null;
    }

    public void setProjectPane(){
        projectPane = new GridPane();
        projectPane.setStyle("-fx-background-color: white;");
        projectPane.setVgap(10);
        projectPane.setHgap(10);

        // add the project number list
        proList = new ListView<Integer>();
        proList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue v, Integer oldV, Integer newV){
                Project pro = getProject(newV.intValue());
                // pass all the values to current project
                currentProject = pro;
                if(pro != null){
                    proTitle.setText(pro.getTitle());
                    proSponsor.setText(pro.getSponsor());
                    proDepartment.setText(String.format("%d", pro.getDNumber()));
                    proBudget.setText(String.format("%.2f", pro.getBudget()));
                }
                else{
                    message.setText(String.format("Cannot find the project %d", newV));
                }
            }
        });

        // add project details
        projectPane.addRow(0, new Label("      Project"));
        projectPane.addRow(0, new Label("information"));

        // set the project list
        for(Project pro: projects){
            proList.getItems().add(pro.getNumber());
        }

        // add project list to the scroll pane
        ScrollPane sp = new ScrollPane();
        sp.setPrefViewportHeight(100);
        sp.setPrefViewportWidth(100);
        sp.setContent(proList);

        projectPane.addRow(1, new Label("Project\nNumber"));
        projectPane.addRow(1, sp);

        // add title
        projectPane.addRow(2, new Label("Title"));
        proTitle = new TextField();
        proTitle.setEditable(false);
        projectPane.addRow(2,proTitle);   

        // add sponsor
        projectPane.addRow(3, new Label("Sponsor"));
        proSponsor = new TextField();
        proSponsor.setEditable(false);
        projectPane.addRow(3, proSponsor);

        // add department
        projectPane.addRow(4, new Label("Department"));
        proDepartment = new TextField();
        proDepartment.setEditable(false);
        projectPane.addRow(4, proDepartment);

        // add budget
        projectPane.addRow(5, new Label("Budget"));
        proBudget = new TextField();
        proBudget.setEditable(false);
        projectPane.addRow(5, proBudget);
    }

    public Project getProject(int num){
        for(Project pro: projects){
            if(pro.getNumber() == num){
                return pro;
            }
        }
        return null;
    }

    public void setEmployeePane(){
        employeesPane = new GridPane();
        employeesPane.setStyle("-fx-background-color: white;");
        employeesPane.setVgap(10);
        employeesPane.setHgap(10);

        // add the employee number list
        emList = new ListView<Integer>();
        emList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue v, Integer oldV, Integer newV){
                Employee e = getEmployee(newV.intValue());
                // pass the values to the current employee
                currentEmployee = e;
                // if there is an employee
                if(e != null){
                    // set the textfield of employee's details
                    eName.setText(e.getName());
                    eDOB.setText(e.getDob());
                    eAddress.setText(e.getAddress());
                    eGender.setText(e.getGender());
                    eSalary.setText(String.format("%.2f", e.getSalary()));
                    eSupervisor.setText(String.format("%d", e.getSupervisor()));
                    eDepartment.setText(String.format("%d", e.getDNumber()));
                    // if employee's type is Admin
                    if(e.getTypes().equals("A")){
                        for(Admin ad: admins){
                            if(ad.getNumber() == e.getNumber()){
                                eSkillLanguage.setText(ad.getSkills());
                                break;
                            }
                        }
                    }
                    // if employee's type is Developer
                    else if(e.getTypes().equals("D")){
                        for(Developer dev: devs){
                            if(dev.getNumber() == e.getNumber()){
                                eSkillLanguage.setText(dev.getLanguages());
                                break;
                            }
                        }
                    }
                    else{
                        eSkillLanguage.setText("Cannot get type");
                    }
                }
                else{
                    message.setText(String.format("Cannot find the employee %d", newV));
                }
            }
        });

        // add employee details
        employeesPane.addRow(0, new Label("      Employee"));
        employeesPane.addRow(0, new Label("information"));

        // set the employee list
        for(Employee e: employees){
            emList.getItems().add(e.getNumber());
        }

        // add employee list to the scroll pane
        ScrollPane sp = new ScrollPane();
        sp.setPrefViewportHeight(100);
        sp.setPrefViewportWidth(100);
        sp.setContent(emList);

        employeesPane.addRow(1, new Label("Employee\nNumber"));
        employeesPane.addRow(1, sp);

        // add name
        employeesPane.addRow(2, new Label("Name"));
        eName = new TextField();
        eName.setEditable(false);
        employeesPane.addRow(2,eName);   

        // add DOB
        employeesPane.addRow(3, new Label("DOB"));
        eDOB = new TextField();
        eDOB.setEditable(false);
        employeesPane.addRow(3, eDOB);

        // add address
        employeesPane.addRow(4, new Label("Address"));
        eAddress = new TextField();
        eAddress.setEditable(false);
        employeesPane.addRow(4, eAddress);

        // add gender
        employeesPane.addRow(5, new Label("Gender"));
        eGender = new TextField();
        eGender.setEditable(false);
        employeesPane.addRow(5, eGender);

        // add salary
        employeesPane.addRow(6, new Label("Salary"));
        eSalary = new TextField();
        eSalary.setEditable(false);
        employeesPane.addRow(6, eSalary);

        // add supervisor
        employeesPane.addRow(7, new Label("Supervisor"));
        eSupervisor = new TextField();
        eSupervisor.setEditable(false);
        employeesPane.addRow(7, eSupervisor);

        // add department
        employeesPane.addRow(8, new Label("Department"));
        eDepartment = new TextField();
        eDepartment.setEditable(false);
        employeesPane.addRow(8, eDepartment);

        // add skill/language
        employeesPane.addRow(9, new Label("Skill/Language"));
        eSkillLanguage = new TextField();
        eSkillLanguage.setEditable(false);
        employeesPane.addRow(9, eSkillLanguage);
    }

    public void setDepartmentPane(){
        departmentPane = new GridPane();
        departmentPane.setStyle("-fx-background-color: white;");
        departmentPane.setVgap(10);
        departmentPane.setHgap(10);

        // add the deparment number list
        depList = new ListView<Integer>();
        depList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue v, Integer oldV, Integer newV){
                Department dep = getDepartment(newV.intValue());
                if(dep != null){
                    deName.setText(dep.getName());
                    deManager.setText(String.format("%d", dep.getManager()));
                    deBudget.setText(String.format("%.2f", dep.getBudget()));
                    deStartDate.setText(dep.getStartDate());
                }
                else{
                    message.setText(String.format("Cannot find the account %d", newV));
                }
            }
        });

        // add department details
        departmentPane.addRow(0, new Label("     Department"));
        departmentPane.addRow(0, new Label("information"));

        // set the department list
        for(Department dep: departments){
            depList.getItems().add(dep.getNumber());
        }

        // add department list to a scroll pane
        ScrollPane sp = new ScrollPane();
        sp.setPrefViewportWidth(100);
        sp.setPrefViewportHeight(100);
        sp.setContent(depList);

        departmentPane.addRow(1, new Label("Department\nNumber"));
        departmentPane.addRow(1, sp);

        // add name
        departmentPane.addRow(2, new Label("Name"));
        deName = new TextField();
        deName.setEditable(false);
        departmentPane.addRow(2, deName);

        // add manager
        departmentPane.addRow(3, new Label("Manager"));
        deManager = new TextField();
        deManager.setEditable(false);
        departmentPane.addRow(3, deManager);

        // add budget
        departmentPane.addRow(4, new Label("Budget"));
        deBudget = new TextField();
        deBudget.setEditable(false);
        departmentPane.addRow(4, deBudget);

        // add start date
        departmentPane.addRow(5, new Label("Start date"));
        deStartDate = new TextField();
        deStartDate.setEditable(false);
        departmentPane.addRow(5, deStartDate);
    }

    public Department getDepartment(int num){
        for(Department dep: departments){
            if(dep.getNumber() == num){
                return dep;
            }
        }
        return null;
    }

    public Employee getEmployee(int num){
        for(Employee e: employees){
            if(e.getNumber() == num){
                return e;
            }
        }
        return null;
    }

    public void loadDepartments(){
        String filename = "departments.txt";
        Path path = Paths.get(filename);
        int cnt = 0;
        try{
            if(Files.exists(path)){
                if(!Files.isDirectory(path)){ // Not a directory, read data
                    Scanner fin = new Scanner(path);
                    fin.useDelimiter(", |\r\n|\n");
                    // Clear the container
                    departments.clear();
                    while(fin.hasNext()){
                        Department dep = new Department();
                        dep.dataInput(fin);
                        departments.add(dep);
                        cnt++;
                    }
                    fin.close();
                }
                else{
                    System.out.printf("File %s does not exisit\n",  path);
                }
            }
        }
        catch(IOException err){
            System.out.println("Exception: " + err);
        }
        if(cnt >= 1){
            System.out.println(String.format("%d departments have been loaded. ", cnt));
        }
        else{
            System.out.println("No departments have been loaded.");
        }
    }

    public void loadEmployees(){
        String filename = "employees.txt";
        Path path = Paths.get(filename);
        int cnt = 0;
        try{
            if(Files.exists(path)){
                if(!Files.isDirectory(path)){ // Not a directory, read data
                    Scanner fin = new Scanner(path);
                    fin.useDelimiter(", |\r\n|\n");
                    // Clear the container
                    employees.clear();
                    while(fin.hasNext()){
                        String line = fin.nextLine();
                        // get the first character of the line
                        char firstLetter = line.charAt(0);

                        // if they are admins
                        if(firstLetter == 'A'){
                            Scanner fin2 = new Scanner(line);
                            fin2.useDelimiter(", |\r\n|\n");

                            Admin admin = new Admin();
                            admin.dataInput(fin2);
                            employees.add(admin);
                            admins.add(admin);

                            cnt++;
                        }
                        else if(firstLetter == 'D'){
                            Scanner fin2 = new Scanner(line);
                            fin2.useDelimiter(", |\r\n|\n");

                            Developer dev = new Developer();
                            dev.dataInput(fin2);
                            employees.add(dev);
                            devs.add(dev);

                            cnt++;
                        }
                    }
                    fin.close();
                }
                else{
                    System.out.printf("File %s does not exist\n",  path);
                }
            }
        }
        catch(IOException err){
            System.out.println("Exception: " + err);
        }
        if(cnt >= 1){
            System.out.println(String.format("%d employees have been loaded. ", cnt));
        }
        else{
            System.out.println("No employees have been loaded.");
        }
    }

    public void loadProjects(){
        String filename = "projects.txt";
        Path path = Paths.get(filename);
        int cnt = 0;
        try{
            if(Files.exists(path)){
                if(!Files.isDirectory(path)){ // Not a directory, read data
                    Scanner fin = new Scanner(path);
                    fin.useDelimiter(", |\r\n|\n");
                    // Clear the container
                    projects.clear();
                    while(fin.hasNext()){
                        Project pro = new Project();
                        pro.dataInput(fin);
                        projects.add(pro);
                        cnt++;
                    }
                    fin.close();
                }
                else{
                    System.out.printf("File %s does not exist\n",  path);
                }
            }
        }
        catch(IOException err){
            System.out.println("Exception: " + err);
        }
        if(cnt >= 1){
            System.out.println(String.format("%d projects have been loaded. ", cnt));
        }
        else{
            System.out.println("No projects have been loaded.");
        }
    }
    
    boolean check = false;
    public void addWorksOn(){
        for(WorksOn w: workson){
            // if currentEmployee is not a developer
            if(!currentEmployee.getTypes().equals("D")){
                message.setText("Please select a developer");
                // check is true
                check = true;
                break;
            }
            // if currentEmployee is already in the works on list
            else if(currentEmployee.getNumber() == w.getENumber() && currentProject.getNumber() == w.getPNumber()){
                message.setText(String.format("Employee %d already works on %d", currentEmployee.getNumber(), currentProject.getNumber()));
                // check is true
                check = true;
                break;
            }
        }
        // if check is false
        if(check == false){
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(5);

            // add textfield of hour
            TextField enterHours = new TextField();
            gp.addRow(0, enterHours);
            
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Enter hours");
            confirm.getDialogPane().setContent(gp);
            // prompt the confirm pane
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.OK){
                // pass all the new details to a new WorksOn object
                WorksOn work = new WorksOn();
                int eNumber = currentEmployee.getNumber();
                int pNumber = currentProject.getNumber();
                String hoursS = enterHours.getText();
                int hours = Integer.parseInt(hoursS);
                work.setHours(hours);
                work.setENumber(eNumber);
                work.setPNumber(pNumber);
                // add new works on to the list
                workson.add(work);
                workList.getItems().add(work.toString());
                message.setText(String.format("Employee %d works on %d is added", currentEmployee.getNumber(), currentProject.getNumber()));
            }
            else{
                message.setText("Cancelled");
            }
        }
        // reset check
        check = false;
    }

    public void deleteWorksOn(){
        // get the select works on index
        int i = workList.getSelectionModel().getSelectedIndex();
        WorksOn work = workson.get(i);
        if(i < 0){
            message.setText("Select work's details");
        }else{
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirm the removal");
            confirm.setContentText("Are you sure?");
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.OK){ // Confirmed
                // Remove the selected works on from the lost
                workList.getItems().remove(i);
                // Remove the account from the array list
                workson.remove(i);
                message.setText("WorksOn element is removed");
            }
            else{
                message.setText("The deletion is cancelled");
            }
        }
    }

    int cntW = 0;
    // save work's details into a text file
    public void saveWorksOn(){
        String filename = "workson.txt";
        try{
            // Open an output file
            Formatter fm = new Formatter(filename);
            for(WorksOn work: workson){
                work.dataOutput(fm);
            }
            fm.close();
            message.setText(String.format("%d workson records are saved", workson.size()));
        }
        catch(IOException err){
            System.out.println("Exception: " + err);
        }
    }

    public void loadWorksOn(){
        String filename = "workson.txt";
        Path path = Paths.get(filename);
        int cnt = 0;
        try{
            if(Files.exists(path)){
                if(!Files.isDirectory(path)){ // Not a directory, read data
                    Scanner fin = new Scanner(path);
                    fin.useDelimiter(", |\r\n|\n");
                    // Clear the container
                    workson.clear();
                    while(fin.hasNext()){
                        WorksOn w = new WorksOn();
                        w.dataInput(fin);
                        workson.add(w);
                        cnt++;
                    }
                    fin.close();
                }
                else{
                    System.out.printf("File %s does not exist\n",  path);
                }
            }
        }
        catch(IOException err){
            System.out.println("Exception: " + err);
        }
        if(cnt >= 1){
            System.out.println(String.format("%d works have been loaded. ", cnt));
        }
        else{
            System.out.println("No works have been loaded.");
        }
    }

    // handle the usages of buttons
    @Override
    public void handle(ActionEvent e){
        // pressed add button
        if((Button)e.getSource() == add){
            addWorksOn();
        }
        else if((Button)e.getSource() == delete){
            deleteWorksOn();
        }
        else if((Button)e.getSource() == save){
            saveWorksOn();
        }
        else{
            message.setText("Wrong button");
        }
    }
}
