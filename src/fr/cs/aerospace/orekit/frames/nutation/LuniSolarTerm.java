package fr.cs.aerospace.orekit.frames.nutation;

/** Class for luni-solar only terms.
 * @author Luc Maisonobe
 */
class LuniSolarTerm extends NutationSerieTerm {

  public LuniSolarTerm(double sinCoeff, double cosCoeff,
                       int cL, int cLPrime, int cF, int cD, int cOmega) {
    super(sinCoeff, cosCoeff);
    this.cL      = cL;
    this.cLPrime = cLPrime;
    this.cF      = cF;
    this.cD      = cD;
    this.cOmega  = cOmega;
  }

  protected double argument(BodiesElements elements) {
    return cL * elements.l + cLPrime * elements.lPrime + cF * elements.f
         + cD * elements.d + cOmega * elements.omega;
  }

  private final int cL; 
  private final int cLPrime; 
  private final int cF; 
  private final int cD; 
  private final int cOmega; 

}
