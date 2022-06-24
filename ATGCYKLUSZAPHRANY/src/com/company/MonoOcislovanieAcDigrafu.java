package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class MonoOcislovanieAcDigrafu {
    private LinkedList<Integer> v;

    private Integer[] aktualIdegVrcholov;


    private Integer[][] H;
    private Integer[] S;

    private int pocetHran;
    private int pocetVrcholov;
    private int pocetVrcholovZ;

    public MonoOcislovanieAcDigrafu(String subor) throws FileNotFoundException {
        this.pocetHran = pocetHran(subor);
        this.H = new Integer[this.pocetHran + 1][3];

        this.dosadH(subor);

        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ() + 1];
        dosadS();
        this.pocetVrcholovZ = pocetVrcholovZ();

        v = new LinkedList<>();

        aktualIdegVrcholov = new Integer[pocetVrcholov+1];


        uvitanie();

        krok1();
        krok2();
        krok3();

       vytlacenieVysledku();


    }

    public int getMonoOcislovanie(int index) {
        return v.get(index-1);
    }

    public void vytlacenieVysledku() {

        for (int j = 0; j < v.size(); j++) {
            System.out.println("\t*\t\t\t\t\t\t" + (j+1) + "\t: " +  v.get(j) );
        }

        System.out.println();
        System.out.println("\t*************************************************************");

    }


    public void krok1() {

        for (int j = 1; j < pocetVrcholov + 1; j++) {
            aktualIdegVrcholov[j] = iDegVrcholu(j);
        }
    }

    public void zmensiIdegVsetkym() {
        int vrchol = 0;
        for (int j = 1; j < pocetVrcholov + 1; j++) {
            if(aktualIdegVrcholov[j] == 0) {
                aktualIdegVrcholov[j] -= 1;
                vrchol = j;
                break;

            }
        }

        if (vrchol < pocetVrcholovZ +1) {
            //kvoli S[] aby nepretieklo
            if (S[vrchol] != null) {
                zmensiIdeg(vrchol);
            }
        }


    }

    public void krok2() {
        for (int j = 1; j < pocetVrcholov + 1; j++) {
            if(aktualIdegVrcholov[j] == 0) {
                zmensiIdegVsetkym();
                v.add(j);
                return;
            }
        }
    }


    public void krok3() {

        do {
            krok2();
        } while (v.size() != pocetVrcholov);


    }

    public void zmensiIdeg(int vrchol) {
        int doKedy;
        if (S[vrchol] == pocetHran || vrchol == pocetVrcholovZ) {
            //ak ma posledny vrchol len jednu hranu
            //alebo
            //ak je r posledny vrchol Z
            doKedy = pocetHran+1;

        }  else  {
            doKedy = S[nextS(vrchol)];
        }

        for (int j = S[vrchol]; j < doKedy; j++) {
            int vrcholDo = H[j][1];
            aktualIdegVrcholov[vrcholDo] -= 1;
        }
    }

    public int iDegVrcholu(int vrchol) {
        int pocitadlo = 0;
        for (int i = 1; i < pocetHran + 1; i++) {


            if (H[i][1] == vrchol) {
                pocitadlo++;
            }
        }
        return pocitadlo;
    }



    public void uvitanie() {
        System.out.println("\t***********Monotone Ocislovanie AcyklickehoGrafu I***********");
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

    public int nextS(int vrchol) {

        for (int j = 1; j < pocetHran +1; j++) {
            if (S[vrchol+j] != null) {
                return H[S[vrchol+j]][0];
            }
        }
        return H[S[vrchol+1]][0];

    }

    public void dosadS() {
        // S[x] pricom x je vrchol sa rovna, na ktorom riadku zacina vrchol x
        for (int i = 1; i < pocetHran + 1; i++) {
            if (!H[i][0].equals(H[i - 1][0])) {
                S[H[i][0]] = i;
            }
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
