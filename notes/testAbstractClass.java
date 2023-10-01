abstract class abstractClass{
    int abstractClassVariable = 100; 

    abstractClass(){
        System.out.println("Constructor: abstractClass");
    }

    public void testMethod(){
        System.out.println("Test");
    }

    abstract void abstractClassMethod();

    // abstract void abstractClassMethodWithBody(){
    //     System.out.println("abstractClassMethodWithBody: abstractClass");
    // }

    /*
     * Abstract methods cannot have a body
    */

    public String toString(){
        return "toString: From abstractClass";
    }
}


class childClass extends abstractClass{

    int childClassVariable = 1;

    public void abstractClassMethod(){
        System.out.println("Implementation of abstractClassMethod in childClass");
    }

    void abstractClassMethodWithBody(){
        System.out.println("abstractClassMethodWithBody: childClass");
    }

    static public void staticMethod(){
        System.out.println("staticMethod: childClass");
    }

    // public String toString(){
    //     return "toString: From childClass";
    // }
}

class testAbstractClass{
    public static void main(String[] args) {
        // Can't do init abstract class
        // abstractClass ac = new abstractClass(); 
        // System.out.println(ac); 

        childClass cc = new childClass();
        System.out.println(cc);
        System.out.println(--cc.abstractClassVariable);
        System.out.println(cc.childClassVariable);
        System.out.println(cc.abstractClassVariable);
        cc.testMethod();
    }
}