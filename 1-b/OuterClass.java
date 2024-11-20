public class OuterClass {
    private int instanceVariable = 10;
    private static int staticVariable = 20;

    static class StaticNestedClass {
        static {
            System.out.println("Static block of StaticNestedClass");
        }

        {
            System.out.println("Instance block of StaticNestedClass");
        }

        public StaticNestedClass() {
            System.out.println("Constructor of StaticNestedClass");
            System.out.println("Accessing static variable from outer class: " + OuterClass.staticVariable);
        }
    }

    final class InnerClass {
        final int finalVariable = 30;

        public final void finalMethod() {
            System.out.println("Final method of InnerClass");
        }
    }

    @Override
    public String toString() {
        return "OuterClass{" +
                "instanceVariable=" + instanceVariable +
                ", staticVariable=" + staticVariable +
                '}';
    }

    public static void staticMethod() {
        System.out.println("Accessing static variable from static method: " + staticVariable);
    }

    public synchronized void synchronizedMethod() {
        staticVariable++;
        System.out.println("Synchronized method: " + staticVariable);
    }

    public static final class UtilityClass {
        public static void staticMethod() {
            System.out.println("UtilityClass static method");
        }
    }

    public static void main(String[] args) {
        // Demonstrate StaticNestedClass
        StaticNestedClass nestedObject = new StaticNestedClass();

        // Demonstrate InnerClass
        OuterClass outerObject = new OuterClass();
        InnerClass innerObject = outerObject.new InnerClass();
        innerObject.finalMethod();

        // Demonstrate static method
        OuterClass.staticMethod();

        // Demonstrate synchronized method
        outerObject.synchronizedMethod();

        // Demonstrate UtilityClass
        UtilityClass.staticMethod();
    }
}