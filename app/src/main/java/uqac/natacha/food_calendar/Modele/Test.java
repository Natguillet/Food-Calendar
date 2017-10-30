package uqac.natacha.food_calendar.Modele;

/**
 * Created by Florian on 22/10/2017.
 */

public class Test {


    public static void main(String[] args){

        AlimentQuantifie a = new AlimentQuantifie(new Aliment("pomme",10,Unite.UNITE),2);

        ListeDeCourse l = new ListeDeCourse();

        System.out.println(l.toString());

        l.ajouterAlimentQuantifie(a);
        System.out.println(l.toString());

        l.ajouterAlimentQuantifie(a);
        System.out.println(l.toString());


    }
}
