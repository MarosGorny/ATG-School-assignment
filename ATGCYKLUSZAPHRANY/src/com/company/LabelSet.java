package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class LabelSet {
    private Integer[][] H;
    private Integer[] S;

    private LinkedList<Integer> E;
    private Integer[] Z;

    private Integer[] t;
    private Integer[] x;

    private int pocetHran;
    private int pocetVrcholov;
    private int u;//zaciatocny vrchol

    public LabelSet(String subor,int zaciatocnyVrchol) throws FileNotFoundException {
        this.pocetHran = pocetHran(subor);
        this.H =  H = new Integer[this.pocetHran+1][3];
        this.dosadH(subor);

        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ()+1];
        dosadS();

        this.u = zaciatocnyVrchol;

        t = new Integer[pocetVrcholov+1];
        x = new Integer[pocetVrcholov+1];
        E = new LinkedList<Integer>();
        Z = new Integer[pocetVrcholov+1];

        uvitanie();

        krok1();
        krok2();
        krok3();


        vypisVysledok();
    }

    public void uvitanie() {
        System.out.println("\t***********LabelSet Algoritmus***********");
        System.out.println("\t\tGraf ma " + pocetHran + " hran");
        System.out.println("\t\tGraf ma " + pocetVrcholov + " vrcholov");
        System.out.println();
    }

    public void krok1() {
        //dosadenie
        for (int i = 1; i < pocetVrcholov +1; i++) {
            x[i] = 0;
            t[i] = Integer.MAX_VALUE/2;

        }
        t[u] = 0;

        E.add(u);
    }

    public void krok2() {
        int doKedy;
        if (S[E.getFirst()] == pocetHran) {
            doKedy = (pocetHran+1) - S[E.getFirst()];
        } else {
            doKedy = S[E.getFirst()+1];
        }


        //????skontroluj ci je spravne dokedy
        for(int i = S[E.getFirst()]; i < doKedy; i++) {
            int j = H[i][1];
            if ( t[j] > t[E.getFirst()] + H[i][2] ) {
                t[j] = t[E.getFirst()] + H[i][2];
                x[j] = E.getFirst();
                E.add(j);
            }
        }
        E.removeFirst();
    }

    public void krok3() {
        do {krok2();} while (!E.isEmpty());
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


    public void vypisVysledok() {
        for (int i = 1; i < pocetVrcholov+1; i++) {
            if (i == u) continue;

            if (t[i] >= 107374191 ) {
                System.out.println("\tZ vrcholu: " + u + " nevedie ziadna cesta do vrcholu: " + i);
            } else {
                System.out.print("\tZ vrcholu: " + u + " do vrcholu " + i + ": " );
                System.out.println(t[i]);
            }
        }
    }


}
