package com.anish.calabashbros;

public class BubbleSorter<T extends Comparable<T>> implements Sorter<T> {

    private T[][] a;

    @Override
    public void load(T[][] a) {
        this.a = a;
    }

    private void swap(int i, int j) {
        T temp;
        int row = a.length;
        int col = a[0].length;
        temp = a[i/row][i%col];
        a[i/row][i%col] = a[j/row][j%col];
        a[j/row][j%col] = temp;
        plan += "" + a[i/row][i%col] + "<->" + a[j/row][j%col] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {
        int row = a.length;
        int col = a[0].length;
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < row*col - 1; i++) {
                if (a[i/row][i%col].compareTo(a[(i + 1)/row][(i+1)%col]) > 0) {
                    swap(i, i + 1);
                    sorted = false;
                }
            }
        }
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}