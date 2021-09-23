/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author escal
 */
public class textProccesor {
    
    
        public int sentenceCoincidence(String originalSentence,String sentenceToCompare){

            ArrayList<String> originalWords = separateSentence(originalSentence);
            ArrayList<String> wordsToCompare = separateSentence(sentenceToCompare);

            int thisSentenceCoincidence = 0;

            for (String originalWord : originalWords) {
                int betterWordCoincidence = 1000;
                for(String wordToCompare : wordsToCompare){
                    int thiswordCoincidence = haveCoincidence(originalWord,wordToCompare);
                    if(thiswordCoincidence < betterWordCoincidence)
                            betterWordCoincidence = thiswordCoincidence;
                }
                thisSentenceCoincidence+=betterWordCoincidence;
            }


        return thisSentenceCoincidence;
    }

    public int haveCoincidence(String keyWord,String testWord ){
        keyWord = keyWord.toLowerCase();
        testWord = testWord.toLowerCase();
        
        int matches = 0;
        int testWordPivot = 0;
        int keyWordPivot = 0;
        
        while(testWordPivot < testWord.length() && keyWordPivot < keyWord.length()){
            if (keyWord.charAt(keyWordPivot) == testWord.charAt(testWordPivot) )
                matches++;
            else
                if (testWordPivot+1 != testWord.length() && keyWord.charAt(keyWordPivot) == testWord.charAt(testWordPivot+1))
                    //hay una letra que sobra,"pan" en "opan"  eliminacion,  colocamos el pivote de tes uno adelante, pues ahora sabemos que ahi continua la palabra
                    testWordPivot++;
                 else if (keyWordPivot+1 != keyWord.length() && keyWord.charAt(keyWordPivot+1) == testWord.charAt(testWordPivot) )
                    //falta una letra,"pan" en "an" ,adicion, colocamos el pivote de la keyword 1 adelante de lo normal, pues ahora sabemos que la siguiente letra es correcta
                    keyWordPivot++;

            keyWordPivot++;
            testWordPivot++;
        }

        return getErrorPercentage(testWord,keyWord,matches);
        
    }

    private int getErrorPercentage(String wordA,String wordB,int matches){
        int wordASize = wordA.length();
        int wordBSize = wordB.length();

        if(wordASize > wordBSize)
            return getPercentage(wordASize,matches);
        else
            return getPercentage(wordBSize,matches);

    }

    private int getPercentage(int may,int men){
        double dMay = may;
        double dMen =  men;

        double percP = (dMay-dMen)/dMay;
        percP =percP*100;
        int perc = (int)percP;

        return perc;
    }

        private ArrayList<String> separateSentence(String sentence){
            String[] words = sentence.split(" ");

        return new ArrayList<>(Arrays.asList(words));
    }
    
}
