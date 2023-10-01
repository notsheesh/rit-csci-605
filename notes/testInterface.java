interface FirstInterface {
    int firstInterfaceVariable = 100;
    public void myMethod(); // interface method

    // public void methodWithImplementation(){
    //     System.out.println("methodWithImplementation: FirstInterface");
    // } 


    public static void staticMethodWithImplementation(){
        System.out.println("staticMethodWithImplementation: FirstInterface");
    }

    /*
     * Abstract methods cannot have a body 
     * This is what is different between Interface and Abstract class
     * In interfaces all functions are by default abstract so we can 
     * never define a body for them
     * 
     * Can use: 
     * Constant variables
     * Abstract methods
     * Cant use concrete methods unless static
     * 
     * Basically 
     * - Either an abstract method so it has no body 
     * - But if you want the method to have a body, it should be static so it 
     * cant be overriden by child class
    */

  }
  
  interface SecondInterface {
    int secondInterfaceVariable = 200;
    public void myOtherMethod(); // interface method
  }
  
  class DemoClass implements FirstInterface, SecondInterface {
    int demoClassVariable = 0;
    public void myMethod() {
      System.out.println("Some text..");
    }
    public void myOtherMethod() {
      System.out.println("Some other text...");
    }

    public void methodWithImplementation(){
        System.out.println("methodWithImplementation: DemoClass");
    }

    public void printInterfaceClassVariables(){
        System.out.println("First Variable: " + firstInterfaceVariable);
        System.out.println("Second Variable: " + secondInterfaceVariable);
        return;
    }
  }
  
  class testInterface {
    public static void main(String[] args) {
      DemoClass myObj = new DemoClass();
      myObj.myMethod();
      myObj.myOtherMethod();
      myObj.printInterfaceClassVariables();

      FirstInterface.staticMethodWithImplementation();
      cc.staticMethodWithImplementation();
    }
  }