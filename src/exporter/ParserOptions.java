package exporter;

public class ParserOptions {
	
	public static final String PAPER_A0 = "a0";
	public static final String PAPER_A1 = "a1";
	public static final String PAPER_A2 = "a2";
	public static final String PAPER_A3 = "a3";
	public static final String PAPER_A4 = "a4";
	
	public static final String ORIENTATION_LANDSCAPE = "landscape";
	public static final String ORIENTATION_PORTRAIT  = "portrait";
	public static final String ORIENTATION_AUTO      = "auto";//identifica se é ORIENTATION_LANDSCAPE ou ORIENTATION_PORTRAIT através das dimensões 
	
	public static final String BOUNDS_RULE_MODELSPACE 		 = "Modelspace";
	public static final String BOUNDS_RULE_MODELSPACE_LIMITS = "Modelspace-Limits";
	public static final String BOUNDS_RULE_PAPERSPACE        = "Paperspace";
	public static final String BOUNDS_RULE_PAPERSPACE_LIMITS = "Paperspace-Limits";
	public static final String BOUNDS_RULE_PAPERSPACE_KABEJA = "Kabeja";
	
	public static final String UNIT_MILIMETER = "mm";
	public static final String UNIT_PIXEL     = "px";
	public static final String UNIT_INCH 	  = "in";
	
	public static final String OUTPUT_STYLE_LAYOUT = "layout";
	public static final String OUTPUT_STYLE_PLOTSETTING = "plotsetting";
	public static final String OUTPUT_STYLE_NAME = "output-style-name";
	
	private String  paper;
	private String  orientation;
	private int     height;
	private int     width;
	private int     dpi;
	private int     quality;
	private String  boundsRule;
	private String  unit;
	private String  outputStyle;
	private boolean svgOverflow;
	private boolean proportional;
	
	public ParserOptions(){
		this.paper 		 = PAPER_A4;
		this.orientation = ORIENTATION_AUTO;
		this.boundsRule  = BOUNDS_RULE_MODELSPACE;
		this.unit        = UNIT_PIXEL;
		this.outputStyle = null;
		this.svgOverflow = false;
		this.proportional = false;
	}
	
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.paper = null;
		this.height = height;
	}
	public int getWidth() {
		this.paper = null;
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getDpi() {
		return dpi;
	}
	public void setDpi(int dpi) {
		this.dpi = dpi;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}

	public String getBoundsRule() {
		return boundsRule;
	}

	public void setBoundsRule(String boundsRule) {
		this.boundsRule = boundsRule;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOutputStyle() {
		return outputStyle;
	}

	public void setOutputStyle(String outputStyle) {
		this.outputStyle = outputStyle;
	}

	public boolean isSvgOverflow() {
		return svgOverflow;
	}

	public void setSvgOverflow(boolean svgOverflow) {
		this.svgOverflow = svgOverflow;
	}

	public boolean isProportional() {
		return proportional;
	}

	public void setProportional(boolean proportional) {
		this.proportional = proportional;
	}		
}
