/**
 * This class provide some useful API to calculate.
 */

/**
 * @author Yang
 * Now provide:
 *  Combi(a,b): [Combination] from a select b
 */
public final class MyMathApi {
    
    static int Combi(int from, int sub) {
        assert(from >= 0 && sub >= 0);
        assert(sub <= from);
        
        int molecule = 1;
        int denominator = 1;
        if(sub == 0 || from == 0){
            return 1;
        }
        else {
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
}
