public class Inheritance {
    public static class Animal {
        //variables
        private String species;
        private String name;
        private int age;

        //constructor
        public Animal(String species, String name, byte age){
            this.species = species;
            this.name = name;
            this.age = age;
            numAnimals++;
        }

        //methods
        public void info(){
            System.out.printf("My name is %s. I am a/an %s, and im %d years old\n",
                                name, species, age);
        }

        //metadata
        public static int numAnimals = 0;
    }

    public static class Dog extends Animal {
        private String breed;

        //constructor
        public Dog(String species, String name, byte age, String breed){
            super(species, name, age);
            this.breed = breed;
        }

        public void speak(){
            System.out.printf("I am a %s %s, I say RARARAR\n",breed,super.species);
        }
    }


    public static void main(String[] args) {
        Dog toto = new Dog("Dog", "Toto", (byte) 9, "Golden Retriever");
        Dog pochita = new Dog("Anime dog", "Pochita", (byte) 120, "Demon");
        Animal cat = new Animal("cat", "garfield", (byte) 15);
        System.out.println(toto.breed);
        System.out.println(cat.name);
        //ACCESSING BREED FROM SAME CLASS = ALLOWED
    }
}