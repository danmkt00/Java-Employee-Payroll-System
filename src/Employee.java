public class Employee {
    private final int id;
    private final String name;
    private final String position;
    private final double salary;

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%-4d | %-20s | %-25s | $%.2f",
                id, name, position, salary);
    }
}

