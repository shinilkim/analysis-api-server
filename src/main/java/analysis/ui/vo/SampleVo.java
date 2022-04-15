package analysis.ui.vo;

import lombok.Data;

@Data
public class SampleVo {
	public SampleVo() {}
	public SampleVo(int id, String name, String title) {
		this.id = id;
		this.name = name;
		this.title = title;
	}
	private int id;
	private String name;
	private String title;
}
