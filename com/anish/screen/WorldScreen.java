package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.BubbleSorter;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;

import asciiPanel.AsciiPanel;

import java.util.Random;

public class WorldScreen implements Screen {

    private World world;
    private Calabash[][] bros;
    String[] sortSteps;

    public WorldScreen() {
        world = new World();

        bros = new Calabash[7][7];

        Random r = new Random();
        int [] random = new int[7*7];


        for(int i = 0;i<7;i++){
            for(int j = 0;j<7;j++){
                int index = r.nextInt(49);
                while(random[index]!=0){
                    index = r.nextInt(49);
                }
                bros[i][j] = new Calabash(new Color(10+10*(index/7)+20*(index%7),10,100), index, world);
                world.put(bros[i][j], i, j);
                random[index]=1;
            }
        }       

/*
        bros[3] = new Calabash(new Color(204, 0, 0), 1, world);
        bros[5] = new Calabash(new Color(255, 165, 0), 2, world);
        bros[1] = new Calabash(new Color(252, 233, 79), 3, world);
        bros[0] = new Calabash(new Color(78, 154, 6), 4, world);
        bros[4] = new Calabash(new Color(50, 175, 255), 5, world);
        bros[6] = new Calabash(new Color(114, 159, 207), 6, world);
        bros[2] = new Calabash(new Color(173, 127, 168), 7, world);

        world.put(bros[0], 10, 10);
        world.put(bros[1], 12, 10);
        world.put(bros[2], 14, 10);
        world.put(bros[3], 16, 10);
        world.put(bros[4], 18, 10);
        world.put(bros[5], 20, 10);
        world.put(bros[6], 22, 10);
*/
        BubbleSorter<Calabash> b = new BubbleSorter<>();
        b.load(bros);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[][] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Calabash getBroByRank(Calabash[][] bros, int rank) {
        for (Calabash bross[] : bros) {
            for(Calabash bro : bross){
                if (bro.getRank() == rank) {
                    return bro;
                }
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(bros, sortSteps[i]);
            i++;
        }

        return this;
    }

}
