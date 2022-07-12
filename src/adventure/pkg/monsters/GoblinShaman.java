package adventure.pkg.monsters;

public class GoblinShaman extends Goblin{

    private int MP;

    public GoblinShaman(){
        super();
        this.setType("Goblin Shaman");
    }

    public int getMP() {return MP;}
    public void setMP(int MP) {this.MP = MP;}
}
