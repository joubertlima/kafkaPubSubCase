package util;

import pub.*;

public final class Constants {

    public final static String customizedKeyTagIptu = "IPTU";
    public final static String customizedKeyTagCofins = "COFINS";
    public final static String customizedKeyTagIcms = "ICMS";
    public final static String[] cities = {"Ouro Preto", "Belo Horizonte", "São Paulo", "Maceió", "Campinas", "Natal", "Floripa", "Brasília"};
    public final static String[] companies ={"Vale do Rio Doce", "Mac Donald's", "Volvo", "IBM", "Itaú", "Heineken"};
    @SuppressWarnings("rawtypes")
	public final static Class[] publishers = {PublisherErrorCodeIPTU.class, PublisherWarningCodeIPTU.class, PublisherCorrectCodeIPTU.class,
            PublisherErrorCodeCOFINS.class, PublisherWarningCodeCOFINS.class, PublisherCorrectCodeCOFINS.class,
            PublisherErrorCodeICMS.class, PublisherWarningCodeICMS.class, PublisherCorrectCodeICMS.class};
    public final static String[] topics = {"iptu", "iptu", "iptu", "cofins", "cofins", "cofins", "icms", "icms", "icms"};
    public final static String[] pubNames = {"IPTU_error_pub", "IPTU_warning_pub", "IPTU_correct_pub", "COFINS_error_pub", "COFINS_warning_pub",
            "COFINS_correct_pub", "ICMS_error_pub", "ICMS_warning_pub", "ICMS_correct_pub"};
    public final static String iptuTopic = "iptu";
    public final static String cofinsTopic = "cofins";
    public final static String icmsTopic = "icms";
    public final static String[] tributes = {"iptu","cofins","icms"};
    public final static String groupID = "eStracta_GROUP_ID";
    public final static int numPartitions = 2;
    public final static int numPubMessages = 80;

}
