/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package teste;
import scanner.*;

/**
 *
 * @author bandeira
 */
public class Teste {
    
    public static void main(String[] args){
        
        BCPLScanner s = new BCPLScanner("examples/program01.bcpl");
        s.getCurrentSpelling();
        System.out.println("HI\n!");
        
        
    }
    
}
