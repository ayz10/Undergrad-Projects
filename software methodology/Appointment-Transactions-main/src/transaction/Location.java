package src.transaction;

/**
 *This enum class represents locations for vaccination sites
 *@author Vineel Reddy
 *@author Alexander Zhao
 */
public enum Location {
    SOMERSET ("Bridgewater", "08807", "SOMSERSET"),
    MIDDLESEX("Piscataway", "08854", "MIDDLESEX"),
    MERCER("Princeton", "085402", "MERCER"),
    MORRIS ("Morristown", "07960", "MORRIS"),
    UNION ("Union", "07083", "UNION");

    private final String city;
    private final String zip;
    private final String county;

    /**
     *Location object constructor
     *@param city city name
     *@param zip zip code
     *@param county county name
     */
    Location(String city, String zip, String county){
        this.city = city;
        this.zip = zip;
        this.county = county;
    }

    /**
     *get zip code
     *@return string zip code
     */
    public String getZip() {
        return this.zip;
    }

    /**
     *Convert location object to string
     *@return string location information
     */
    @Override
    public String toString(){
        return city + " " +zip + ", " + county;
    }
}
