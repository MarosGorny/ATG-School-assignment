package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NajneskorNutnychKoncovII {
    private Integer[] P;
    private Integer[] time;

    private Integer[][] H;
    private Integer[] S;

    private Integer[] k;
    private Integer[] y;



    private int pocetHran;
    private int pocetVrcholov;
    private int pocetVrcholovZ;
    private String subor;

    public NajneskorNutnychKoncovII(String subor) throws FileNotFoundException {
        this.subor = subor;


        this.pocetHran = pocetHran(subor);
        this.H = new Integer[this.pocetHran + 1][3];

        this.dosadH(subor);

        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ() + 1];
        dosadS();
        this.pocetVrcholovZ = pocetVrcholovZ();

        k = new Integer[pocetVrcholov + 1];
        y = new Integer[pocetVrcholov + 1];

        time = new Integer[pocetVrcholov+1];
        dosadTime("TechnoTabulkaTime");

        P = new Integer[pocetVrcholov+1];


        uvitanie();

        krok1();
        krok2();
        krok3();

        vypisVysledok();

    }

    public void krok1() throws FileNotFoundException {
        MonoOcislovanieAcDigrafu monoOcislovanieAcDigrafu = new MonoOcislovanieAcDigrafu(subor);
        for (int i = 1; i < pocetVrcholov + 1 ; i++) {
            P[i] = monoOcislovanieAcDigrafu.getMonoOcislovanie(i);
        }
    }

    public void krok2() throws FileNotFoundException {
        NajskorMozneZaciatkyII najskorMozneZaciatkyII = new NajskorMozneZaciatkyII(subor);
        int trvanieProjektu = najskorMozneZaciatkyII.getT();
        for (int i = 1; i < pocetVrcholov+1; i++) {
            k[i] = trvanieProjektu;
            y[i] = 0;
        }
    }

    public void krok3() {
        for (int i = pocetVrcholov; i > 0 ; i--) {
            int r = P[i];

            if(i > pocetVrcholovZ) {
                continue;
            }

            int doKedy;
            if (S[r] == pocetHran || r == pocetVrcholovZ) {
                //ak ma posledny vrchol len jednu hranu
                //alebo
                //ak je r posledny vrchol Z
                doKedy = pocetHran+1;

            }  else  {
                doKedy = S[r+1];
            }


            for (int j = S[r]; j < doKedy; j++) {
                int w = H[j][1];
                if (k[r] > k[w] - time[w]) {
                    k[r] = k[w] - time[w];
                    y[r] = w;
                }
            }
        }


    }

    public void vypisVysledok() {
        System.out.println("\t\t Vypocet najneskor nutnych koncov");
        for (int i = pocetVrcholov; i > 0; i--) {

            System.out.println("\t\t\tk" + "(" + i + ") = " + k[i] );
        }

    }

    public void uvitanie() {
        System.out.println();
        System.out.println("\t**********Algoritmus II. na urcenie najneskor nutnych koncov***********");
        System.out.println("\t\tGraf ma " + pocetHran + " hran");
        System.out.println("\t\tGraf ma " + pocetVrcholov + " vrcholov");
        System.out.println();
    }




    public int pocetHran(String subor) throws FileNotFoundException {
        File hrany = new File(subor);
        Scanner scannerHrany = new Scanner(hrany);
        int lineNumber = 0;
        while (scannerHrany.hasNextLine()) {
            String line = scannerHrany.nextLine();
            lineNumber++;
        }

        return lineNumber;
    }

    public int pocetVrcholovPoDosadeniH() {
        int maxZ = 0;
        int maxDo = 1;
        for (int i = 1; i < pocetHran + 1; i++) {
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

        for (int i = 1; i < pocetHran + 1; i++) {
            if (H[i][0] >= maxZ) maxZ = H[i][0];
        }

        return maxZ;

    }



    public void dosadS() {
        // S[x] pricom x je vrchol sa rovna, na ktorom riadku zacina vrchol x
        for (int i = 1; i < pocetHran + 1; i++) {
            if (!H[i][0].equals(H[i - 1][0])) {
                S[H[i][0]] = i;
            }
        }
    }

    public void dosadTime(String subor) throws FileNotFoundException {
        File time = new File(subor);
        Scanner scanner = new Scanner(time);
        int lineNumber = 1;
        while (scanner.hasNextInt()) {
            int cislo = scanner.nextInt();

            this.time[lineNumber] = cislo;
            lineNumber++;
        }
    }

    public void dosadH(String subor) throws FileNotFoundException {
        File hrany = new File(subor);
        Scanner scannerHrany = new Scanner(hrany);

        int pocitadlo = 0;
        int lineNumber = 1;
        while (scannerHrany.hasNextInt()) {
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
