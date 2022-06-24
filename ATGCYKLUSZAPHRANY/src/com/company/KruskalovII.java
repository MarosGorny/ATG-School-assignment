package com.company;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedList;
import java.util.Scanner;

public class KruskalovII {
    private Integer[][] H;
    private Integer[][] HCopy;
    private Integer[] S;
    private LinkedList<Integer> kostra;
    private LinkedList<Integer> P;
    private Integer[] k;



    private int pocetHran;
    private int pocetVrcholov;
    private int pocetVrcholovZ;

    public KruskalovII(String subor) throws FileNotFoundException {
        this.pocetHran = pocetHran(subor);
        this.HCopy = new Integer[this.pocetHran+1][3];
        this.H = new Integer[this.pocetHran+1][3];


        this.dosadH(subor);

        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ()+1];
        dosadS();
        this.pocetVrcholovZ = pocetVrcholovZ();

        skopiruj2Dpole(H,HCopy);

        k = new Integer[pocetVrcholov+1];

        kostra = new LinkedList<Integer>();
        P = new LinkedList<Integer>();

        uvitanie();
        krok1();
        krok2();
        krok3();
        krok4();


        vypisVysledku();


    }

    public void vypisVysledku() {
        System.out.println("\t\tNajlacnejsia kostra grafu je zlozena z hran:");

        for (int i = 0; i < kostra.size(); i++) {
            System.out.println("\t\t\t{" + H[kostra.get(i)][0] + "," + H[kostra.get(i)][1] + "}");
        }
    }

    public void krok4() {

        do {krok3();} while (kostra.size() != (pocetVrcholov -1) || !P.isEmpty() );
    }
    public void krok3() {
        int akutalnaHrana = P.getFirst();
        int u = H[akutalnaHrana][0];
        int v = H[akutalnaHrana][1];
        P.removeFirst();

        if (k[u] != k[v]){
            int kmin;
            int kmax;
            kostra.add(akutalnaHrana);
            if (k[u] < k [v]) {
                kmin = k[u];
                kmax = k[v];
            } else {
                kmin = k[v];
                kmax = k[u];
            }

            for (int i = 1; i < pocetVrcholov + 1; i++) {
                if (k[i] == kmax) {
                    k[i] = kmin;
                }
            }
        }
    }

    public void krok2(){
        for (int i = 1; i < pocetVrcholov+1; i++) {
            k[i] = i;
        }
    }

    public void vypisP() {
        for (int p:P ) {
            System.out.println(p);
        }
    }

    public void krok1() {
        //zoradeniePOdMinPoMax
        for (int j = 1; j < pocetHran + 1; j++) {
            int minimum = HCopy[j][2];
            int index = 1;
            for (int i = 1; i < pocetHran +1; i++) {
                if (HCopy[i][2] < minimum) {
                    index = i;
                    minimum = HCopy[i][2];
                }

            }

            HCopy[index][2] = Integer.MAX_VALUE;

            P.add(index);

        }

    }
    public void uvitanie() {
        System.out.println("\t***********Kruskal II***********");
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

    public void skopiruj2Dpole(Integer[][] poleZ, Integer[][] poleDo ) {
        for (int i = 0; i < poleZ.length; i++) {
            for (int j = 0; j < poleZ[i].length ; j++) {
                poleDo[i][j] = poleZ[i][j];
            }
        }

    }
}
