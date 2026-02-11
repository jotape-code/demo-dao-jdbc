package Application;

import java.util.Comparator;

public class ComparatorTeste implements Comparator<Integer> {

    @Override
    public int compare(Integer i1, Integer i2) {
        if(i1 > i2){
            return -1;
        }
        else if(i2 > i1){
            return 1;
        }
        else{
            return 0;
        }
    }
    
}
