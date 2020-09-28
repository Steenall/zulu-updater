package zulu;

public class Zulu {
	
	private static final String url = "https://api.azul.com/zulu/download/community/v1.0/bundles/latest/";
	private static final String binary = "binary/";
	
	private JavaVersion javaVersion;
	private String buildId;
	private OS os;
	private Architecture architecture;
	private JavaPackage javaPackage;
	
	public Zulu() {
		
	}
	protected Zulu(JavaVersion javaVersion, String buildId, OS os, Architecture architecture, JavaPackage javaPackage) {
		this.javaVersion = javaVersion;
		this.buildId = buildId;
		this.os = os;
		this.architecture = architecture;
		this.javaPackage = javaPackage;
	}
	public boolean memeType(Zulu other) {
		return (javaVersion.equals(((Zulu) other).getJavaVersion()))&&
				(os.equals(((Zulu) other).getOs()))&&
				(architecture.equals(((Zulu) other).getArchitecture()))&&
				(javaPackage.equals(((Zulu) other).getJavaPackage()));
	}
	@Override
	public boolean equals(Object other) {
		return (other instanceof Zulu)&&
				memeType((Zulu) other)&&
				(javaPackage.equals(((Zulu) other).getJavaPackage()));
	}
	public JavaVersion getJavaVersion() {
		return javaVersion;
	}
	public void setJavaVersion(int javaVersion) {
		for(JavaVersion i: JavaVersion.values()) {
			if(i.getVersion()==javaVersion) {
				this.javaVersion=i;
				return ;
			}
		}
	}
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	public void setOs(String os) {
		for(OS i : OS.values()) {
			if(i.getNom().toLowerCase().equals(os.toLowerCase())) {
				this.os=i;
				return;
			}
		}
	}
	public void setArchitecture(String architecture) {
		for(Architecture i : Architecture.values()) {
			for(String j :i.getNomPotentiel()) {
				if(j.contains(architecture.toLowerCase())) {
					this.architecture=i;
					return;
				}
			}
		}
	}
	public void setJavaPackage(JavaPackage javaPackage) {
		this.javaPackage=javaPackage;
	}
	public String getBuildId() {
		return buildId;
	}
	public OS getOs() {
		return os;
	}
	public Architecture getArchitecture() {
		return architecture;
	}
	public JavaPackage getJavaPackage() {
		return javaPackage;
	}
	private String getURLExtension() {
		String fx="";
		String jre="";
		if(!javaPackage.estJDK())jre="&bundle_type=jre";
		if(javaPackage.possedeFX())fx="&features=fx";
		return "?jdk_version="+javaVersion.getVersion()+"&os="+os.getNom().toLowerCase()+"&arch="+architecture.getArch()+"&hw_bitness="+architecture.getBitness()+fx+jre;
	}
	public String getDownloadURL() {
		// TODO Auto-generated method stub
		return url+binary+getURLExtension();
	}
	public String getURL() {
		// TODO Auto-generated method
		return url+getURLExtension();
	}
	@Override
	public Zulu clone() {
		return new Zulu(javaVersion, buildId, os, architecture, javaPackage);
	}
	@Override
	public String toString() {
		return "Zulu "+javaPackage+" "+javaVersion+" "+buildId+" pour "+os+" "+architecture;
	}
	public void setArchitecture(Architecture architecture) {
		this.architecture=architecture;		
	}
	public void setOs(OS os) {
		this.os=os;
	}
}
