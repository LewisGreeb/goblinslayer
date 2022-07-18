package adventure.pkg.monsters;

import java.util.Random;

public class Nilbog extends Monster{

    // Attributes.
    private String riddle;
    private String answer;
    private int answerCount;

    // Constructor.
    public Nilbog(){
        super(1,1,null, null, "Nilbog", 200);
        // Randomise riddle selection.
        Random rand = new Random();
        setRiddle(rand.nextInt(8));
        this.answerCount = 0;
    }

    private void setRiddle(int rInt){
        switch(rInt){
            case 0 -> {
                this.riddle = "What goes on four legs in the morning, two legs in the afternoon, and three legs in the evening?";
                this.answer = "A man";
            }
            case 1 -> {
                this.riddle = "Alive without breath, as cold as death; Never thirsty, ever drinking; All in mail never clinking.";
                this.answer = "A fish";
            }
            case 2 -> {
                this.riddle = "Never resting, never still; moving silently from hill to hill; it does not walk, run or trot; all is cool where it is not.";
                this.answer = "The sun";
            }
            case 3 -> {
                this.riddle = "What can run, but never walks; has a mouth, but never talks; has a head, but never weeps; has a bed, but never sleeps?";
                this.answer = "A river";
            }
            case 4 -> {
                this.riddle = "This thing all things devour: birds, beasts, trees, flowers; gnaws iron, bites steel; grinds hard stones to meal; slays kings, ruins towns; and beats high mountains down.";
                this.answer = "Time";
            }
            case 5 -> {
                this.riddle = "At night they come without being fetched, and by day they are lost without being stolen.";
                this.answer = "Stars";
            }
            case 6 -> {
                this.riddle = "Voiceless it cries, wingless flutters, toothless bites, mouthless mutters.";
                this.answer = "The wind";
            }
            case 7 -> {
                this.riddle = "What has roots as nobody sees, is taller than trees; up, up it goes, and yet never grows?";
                this.answer = "A mountain";
            }
        }
    }

    public String getRiddle(){
        return this.riddle;
    }

    public boolean compareAnswer(String answer){
        // Lower case both strings to improve chance of being correct.
        String ans = this.answer.toLowerCase();
        answer = answer.toLowerCase();
        // Return comparison.
        return ans.contains(answer);
    }

    public int getAnswerCount(){return this.answerCount;}
    public void incrementAnswerCount(){this.answerCount++;}
}
