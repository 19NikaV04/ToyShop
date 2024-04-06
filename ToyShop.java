import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.*;

class ToyShop{
    public static List<Toy> toys;
	public static List<WonToy> wonToys;
	private static int id = 1;
   
    public ToyShop(){
        toys = new ArrayList<>();
		wonToys = new ArrayList<>();
    }

	public static void main(String[] args){
		ToyShop toyShop = new ToyShop();
		while (true){
			try{
				int action = getAction();
				switch (action){
					case 1: 
						addToy();
						System.out.println("----------------------------------------------------------");
						break;
					case 2: 
						startLotery();
						System.out.println("----------------------------------------------------------");
						break;
					case 3: 
						printAllToys();
						System.out.println("----------------------------------------------------------");
						break;
					case 4: 
						printWonToys();
						System.out.println("----------------------------------------------------------");
						break;
					case 5: 
						changeToyWeight();
						System.out.println("----------------------------------------------------------");
						break;
					case 6: return;
					case 7: saveToFile();
						System.out.println("----------------------------------------------------------");
					break;
					default: 
				}
			}
			catch (Exception e){
				System.out.println("Incorrect format of enter data! Try again!\nReason: " + e.getMessage());
				System.out.println("----------------------------------------------------------");
			}
		}
	}
	
	public static void addToy(){
		System.out.print("\nEnter toy name: ");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		
		System.out.print("Enter toy quantity: ");
		scanner = new Scanner(System.in);
		int quantity = Integer.parseInt(scanner.nextLine());
		if (quantity < 0){
			System.out.println("Toy quantity should be greater than 0!");
            return;	
		}
		
		System.out.print("Enter toy weight: ");
		scanner = new Scanner(System.in);
		double weight = Double.parseDouble(scanner.nextLine());
		
		if (weight > 1.0 || weight < 0.0){
			System.out.println("Toy weight should be between 0 and 1!");
            return;	
		}
		toys.add(new Toy(id++, name, quantity, weight));
		
		toys.sort(Comparator.comparing(Toy::getWeight).reversed());
		System.out.println("New toy was added");
	}

	public static void startLotery(){
		System.out.println();
		System.out.print("Enter your name: ");
		Scanner scanner = new Scanner(System.in);
		String winner = scanner.nextLine();

		Toy wonToy = getToyThroughLotery();
		if (wonToy != null)
			wonToys.add(new WonToy(wonToy.getId(), wonToy.getName(), winner));
	}
	
	public static Toy getToyThroughLotery(){
		for(Toy toy : toys){
			if (toy.getQuantity() > 0){
				if (Math.random() <= toy.getWeight()){
					toy.setQuantity(toy.getQuantity() - 1);
					System.out.println("You won this toy: " + toy.getName());
					return toy;
				}
			}
		}
		System.out.println("Unfortunately, You did not win. Please, try later!");
		return null;
	}
	
	public static void changeToyWeight(){
		printAllToys();
		System.out.println();
		System.out.print("Enter id of toy change weight: ");
		Scanner scanner = new Scanner(System.in);
		int id = Integer.parseInt(scanner.nextLine());
		for (Toy toy : toys){
			if (toy.getId() == id){
				System.out.print("Enter new toy weight: ");
				scanner = new Scanner(System.in);
				double weight = Double.parseDouble(scanner.nextLine());
				if (weight > 1.0 || weight < 0.0){
					System.out.println("Toy weight should be between 0 and 1!");
					return;	
				}
				toy.setWeight(weight);
				System.out.println("Toy weight was changed!");
				toys.sort(Comparator.comparing(Toy::getWeight).reversed());
				return;
			}
		}
		System.out.println("Toy with chosen ID was not found!");		
	}
	
	public static void saveToFile(){
		String filename = "toys.txt";
		try(PrintWriter write = new PrintWriter(new FileWriter(filename))){
			for (WonToy toy : wonToys)
				write.printf("Id = %d; Name = %s; Winner = %s\n", toy.getId(), toy.getName(), toy.getWinner());
			System.out.println();
			System.out.println("Info about won toys were saved to file " + filename);
		}
		catch(IOException ex){
			System.err.println("Error during printing to file: " + ex.getMessage());
		}
	}
	
	public static void printAllToys(){
		System.out.println("\nID\tNAME\tQUANTITY\tWEIGHT");
		for (Toy toy : toys){
			System.out.print(toy.getId() + "\t");
			System.out.print(toy.getName() + "\t");
			System.out.print(toy.getQuantity() + "\t\t");
			System.out.println(toy.getWeight());
		}
	}
	
	public static void printWonToys(){
		System.out.println("\nID\tNAME\tWINNER");
		for (WonToy toy : wonToys){
			System.out.print(toy.getId() + "\t");
			System.out.print(toy.getName() + "\t");
			System.out.println(toy.getWinner());
		}
	}
	
	private static int getAction(){
		System.out.println("\nChoose action:");
		System.out.println("1. Add toy");
		System.out.println("2. Start lotery");
		System.out.println("3. Print all toys");
		System.out.println("4. Print won toys");
		System.out.println("5. Change toy weight");
		System.out.println("6. Quit");
		System.out.println("7. Save to file");
		
		System.out.print("\nEnter: ");
		
		Scanner scanner = new Scanner(System.in);
		return Integer.parseInt(scanner.nextLine());
	}
	
	public static class Toy{
		protected int id;
		protected String name;
		private int quantity;
		private double weight;

		public Toy(){}
		
		public Toy(int id, String name, int quantity, double weight){
			this.id = id;
			this.name = name;
			this.quantity = quantity;
			this.weight = weight;
		}

		public int getId(){
			return id;
		}

		public String getName(){
			return name;
		}

		public double getWeight(){
			return weight;
		}

		public int getQuantity(){
			return quantity;
		}
		
		public void setQuantity(int quantity){
			this.quantity = quantity;
		}

		public void setWeight(double weight){
			this.weight = weight;
		}
	}

	public static class WonToy extends Toy{
		private String winner;
		
		public WonToy(){}
		
		public WonToy(int id, String name, String winner){
			this.id = id;
			this.name = name;
			this.winner = winner;
		}

		public String getWinner(){
			return this.winner;
		}
	}
}
