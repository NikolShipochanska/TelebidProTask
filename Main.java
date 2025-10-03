import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] firstInput = scanner.nextLine().split(" ");
        int countGoat = Integer.parseInt(firstInput[0]);
        int maxCountOfCourses = Integer.parseInt(firstInput[1]);

        int[] goatWeightInput = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        List<Integer> goats = new ArrayList<>();

        for (int weight : goatWeightInput) {
            goats.add(weight);
        }

        int maxWeight = 0;

        for(int goat : goats){
            if(goat > maxWeight){
                maxWeight = goat;
            }
        }

        int totalSum = 0;
        for(int goat : goats){
            totalSum += goat;
        }
        int low = maxWeight; // минимален капацитет (долна граница) на възможен капацитет
        int high = totalSum; // горна граница на възможния капацитет

        while(low < high){
            int mid = low + (high - low) / 2;
            if(canTransport(mid,goats,maxCountOfCourses)){
                high = mid;
            }else{
                low = mid +1;
            }
        }

        System.out.println(low);
    }

    private static boolean canTransport(int capacity,List<Integer> origGoats,int maxCountOfCourses){
        List<Integer> remaining = new ArrayList<>(origGoats);
        int trips = 0;
        while(!remaining.isEmpty()){
            trips++;
            if(trips > maxCountOfCourses){
                return false;
            }
            int currentLoad = 0; // текущо сумарно тегло на козите, превозени в един курс
            while(true){
                int maxFit = -1; // най-тежката, която се побира, сред останалите
                int indexToRemove = -1;
                for(int i =0; i< remaining.size();i++){
                    int weight = remaining.get(i);
                    if(weight <= capacity - currentLoad && weight > maxFit){ /* проверява дали текущото тегло се побира
                     в оставащия капацитет и дали текущото тегло е по-голямо от най-голямото, намерено досега, което може да се побере */
                        maxFit = weight;
                        indexToRemove = i;
                    }
                }
                if(indexToRemove == -1){
                    break;
                }
                currentLoad += maxFit;
                remaining.remove(indexToRemove);
            }
        }
        return  true;
    }
}
