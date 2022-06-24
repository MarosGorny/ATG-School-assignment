package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ZakladnyAlgNajkrtsichOrientovanychUVciest {
    private Integer[][] H;
    private Integer[] S;

    private Integer[] x;
    private Integer[] t;

    private int pocetHran;
    private int pocetVrcholov;
    private int u;

    public ZakladnyAlgNajkrtsichOrientovanychUVciest(String subor, int u) throws FileNotFoundException {
        this.u = u;
        this.pocetHran = pocetHran(subor);
        this.H = new Integer[this.pocetHran+1][3];
        this.dosadH(subor);


        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ()+1];
        dosadS();

        t = new Integer[pocetVrcholov+1];
        x = new Integer[pocetVrcholov+1];


        uvitanie();

        krok1();
        krok2();

        vypisVysledok();


    }

    public void krok1() {
        for (int i = 0; i < pocetVrcholov + 1; i++) {
            x[i] = 0;
            t[i] = Integer.MAX_VALUE/2;
        }
        t[u] = 0;
    }

    public void krok2() {
        boolean existuje = true;
        do {
            int pocitadlo = 0;
            for (int k = 1; k < pocetHran+1; k++) {
                int i = H[k][0];
                int j = H[k][1];
                int c = H[k][2];
                if (t[j] > t[i] + c) {
                    t[j] = t[i] + c;
                    x[j] = i;
                    pocitadlo++;
                }
            }
            if (pocitadlo == 0) existuje = false;
        } while (existuje);


    }

    public void vypisVysledok() {
        for (int i = 1; i < pocetVrcholov+1; i++) {
            if (i == u) continue;

            if (t[i] >= 107374191 ) {
                System.out.println("\t\tZ vrcholu: " + u + " nevedie ziadna cesta do vrcholu: " + i);
            } else {
                System.out.print("\t\tZ vrcholu: " + u + " do vrcholu " + i + ": " );
                System.out.println(t[i]);
            }
        }
    }

    public void uvitanie() {
        System.out.println("\t***********Zakladny cyklus najkrtsich orientovanych u-v ciest ***********");
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
