import java.util.ArrayList;

public class Wyb {
    Exdb exdb = new Exdb();




    public ArrayList<Przepis> choT(int a){


        switch(a){
            case 1:
                return exdb.getType("obiad");

            case 2:

                return exdb.getType("deser");
            default:
                System.out.println("Error");
                return null;
        }

    }







}
