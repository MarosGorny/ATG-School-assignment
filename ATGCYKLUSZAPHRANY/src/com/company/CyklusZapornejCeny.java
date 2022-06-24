package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class CyklusZapornejCeny {
    private Integer[][] H;
    private Integer[] S;
    private LinkedList<Integer> E;
    private Integer[] t;
    private Integer[] x;

    private ArrayList<Integer> kontrola;

    private int pocetHran;
    private int pocetVrcholov;
    private int pocetVrcholovZ;

    public CyklusZapornejCeny(String subor) throws FileNotFoundException {
        this.pocetHran = pocetHran(subor);
        this.H = new Integer[this.pocetHran+1][3];
        this.dosadH(subor);

        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ()+1];
        dosadS();
        this.pocetVrcholovZ = pocetVrcholovZ();

        t = new Integer[pocetVrcholov+1];
        x = new Integer[pocetVrcholov+1];
        E = new LinkedList<Integer>();
        kontrola = new ArrayList<>();


        uvitanie();

        krok1Inic();
        krok2();
        krok3();
    }

    public void krok1Inic() {
        for (int i = 1; i < pocetVrcholov + 1; i++) {
            t[i] = 0;
            x[i] = 0;
            E.add(i);
        }
    }

    public void krok2(){
        int r = E.getFirst();
        E.removeFirst();

        if (r <= pocetVrcholovZ) {
            int doKedy;

            if (S[r] == pocetHran || r == pocetVrcholovZ) {
                //ak ma posledny vrchol len jednu hranu
                //alebo
                //ak je r posledny vrchol Z
                doKedy = pocetHran+1;

            }  else  {
                doKedy = S[r+1];
            }

            for (int i = S[r]; i < doKedy; i++) {
                int j = H[i][1];
                if (t[j] > t[r] + H[i][2]) {
                    t[j] = t[r] + H[i][2];
                    x[j] = r;

                    boolean pridaj = true;
                    for (int e:E) {
                        if(e == j) {
                            pridaj = false;
                        }
                    }
                    if (pridaj) {
                        E.add(j);
                    }


                }
                kontrola.clear();
                kontrola(j);
            }
        }

    }

    public void krok3(){

        do {krok2();} while (!E.isEmpty());
        System.out.println("\tV digrade neexistuje cyklus zapornej ceny");

    }

    public void kontrola(int j) {
        int n = x[j];

        if (kontrola.contains(j)) {
            System.out.println("\tCyklus zapornej ceny existuje");
            System.exit(-1);
        }
        kontrola.add(j);


//        if (n==j) {
//            System.out.println("\tCyklus zapornej ceny existuje");
//            System.exit(-1);
//        }
        if (n == 0) {
            return;
        }

        kontrola(n);
    }

    public void uvitanie() {
        System.out.println("\t***********Cyklus zapornej ceny***********");
        System.out.println("\t\tGraf ma " + pocetHran + " hran");
        System.out.println("\t\tGraf ma " + pocetVrcholov + " vrcholov");
        System.out.println();
    }

    public int pocetHran(String subor) throws FileNotFoundException {
        File hrany = new File(subor);
        Scanner scannerHrany = new Scanner(hrany);
        int lineNumber = 0;
        while(scannerHrany.hasNextLine()){
            String line = scannerHrany.nextLine();
            lineNumber++;
        }

        return lineNumber;
    }

    public int pocetVrcholovPoDosadeniH() {
        int maxZ = 0;
        int maxDo = 1;
        for (int i = 1; i < pocetHran+1; i++) {
            if (H[i][0] >= maxZ) maxZ = H[i][0];
            if (H[i][1] >= maxDo) maxDo = H[i][1];
        }

        if (maxZ >= maxDo) {
            return maxZ;
        } else {
            return maxDo;
        }


    }

    public int pocetVrcholovZ() {
        int maxZ = 0;

        for (int i = 1; i < pocetHran+1; i++) {
            if (H[i][0] >= maxZ) maxZ = H[i][0];
        }

        return maxZ;

    }

    public void dosadS() {
        // S[x] pricom x je vrchol sa rovna, na ktorom riadku zacina vrchol x
        int  pocitadlo = 1;
        for (int i = 1; i < pocetHran+1; i++) {
            if (!H[i][0].equals(H[i - 1][0])) {
                S[pocitadlo] = i;
                pocitadlo++;

            }
        }
    }

    public void dosadH(String subor) throws FileNotFoundException {
        File hrany = new File(subor);
        Scanner scannerHrany = new Scanner(hrany);

        int pocitadlo = 0;
        int lineNumber = 1;
        while(scannerHrany.hasNextInt()){
            int cislo = scannerHrany.nextInt();

            this.H[lineNumber][pocitadlo] = cislo;
            pocitadlo++;

            if (pocitadlo == 3) {
                pocitadlo = 0;
                lineNumber++;
            }

        }

    }

}


