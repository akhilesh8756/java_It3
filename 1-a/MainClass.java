class ClassA {
    private int varA;

    public ClassA(int varA) {
        this.varA = varA;
        System.out.println("ClassA constructor called");
    }

    public ClassA(ClassA other) {
        this.varA = other.varA;
    }

    public int getVarA() {
        return varA;
    }

    public void setVarA(int varA) {
        this.varA = varA;
    }
}

class ClassB extends ClassA {
    protected int varB;

    private ClassB() {
        super(0);
    }

    public ClassB(int varA, int varB) {
        super(varA);
        this.varB = varB;
        System.out.println("ClassB constructor called");
    }

    public ClassB(ClassB other) {
        super(other);
        this.varB = other.varB;
    }

    public int getVarB() {
        return varB;
    }

    public void setVarB(int varB) {
        this.varB = varB;
    }
}

class ClassC extends ClassB {
    public int varC;

    public ClassC(int varA, int varB, int varC) {
        super(varA, varB);
        this.varC = varC;
        System.out.println("ClassC constructor called");
    }

    public ClassC(ClassC other) {
        super(other);
        this.varC = other.varC;
    }

    public void display() {
        System.out.println("ClassA varA: " + getVarA());
        System.out.println("ClassB varB: " + getVarB());
        System.out.println("ClassC varC: " + varC);
    }
}

public class MainClass {
    public static void main(String[] args) {
        ClassC obj1 = new ClassC(10, 20, 30);
        obj1.display();

        ClassC obj2 = new ClassC(obj1);
        System.out.println("\nValues of copied object:");
        obj2.display();
    }
}
