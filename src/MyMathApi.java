/**
 * This class provide some useful API to calculate.
 */

/**
 * @author Yang
 * Now provide:
 *  Combi(a,b): [Combination] from a select b
 */
public final class MyMathApi {
    
    static long Combi(int from, int sub) {
        assert(from >= 0 && sub >= 0);
        assert(sub <= from);
        
        long molecule = 1;
        long denominator = 1;
        if(sub == 0 || from == 0){
            return 1;
        }
        else if(sub <= from/2){
            for(int i=0; i < sub; i++) {
                molecule *= from - i;
            }
            for(int i=sub; i > 0; i--) {
                denominator *= i;
            }
            return molecule/denominator;
        }
        else { //Optimization the calculate process
            sub = from - sub;
            for(int i=0; i < sub; i++) {
                molecule *= from - i;
            }
            for(int i=sub; i > 0; i--) {
                denominator *= i;
            }
            return molecule/denominator;
            
        }
    }
    
    static double sigmoid(double tTx) {
        return 1.0 / (1 + Math.exp(-tTx));
    }
    
    static int sumInt(int[] intArry) {
        int result = 0;
        for(int i : intArry) {
            result += i;
        }
        return result;
    }
}
