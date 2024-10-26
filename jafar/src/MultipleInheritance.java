import java.util.Scanner;
public class MultipleInheritance{
    interface Person{
        private void talk(){
            System.out.println("Hi!");
        }
    }

    interface Animal{
        public void info();
    }

    public static class Hybrid implements Person, Animal{
        private String name;
        private String breed;

        public void talk(){
            System.out.println("My name is " + name);
        }

        public void info(){
            System.out.println("I am a hybrid of human and " + breed);
        }

        public Hybrid(String name, String breed){
            this.name = name;
            this.breed = breed;
        }
    }

    public static void main(String[ ] args){
        Hybrid medusa = new Hybrid("Medusa", "Snake");
        System.out.println(medusa.name);
        medusa.talk();
        medusa.info();
    }
}