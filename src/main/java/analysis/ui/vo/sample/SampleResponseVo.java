package analysis.ui.vo.sample;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "샘플 응답 모델")
public class SampleResponseVo<T> {
	public SampleResponseVo() {}
	
	public SampleResponseVo(T data) {
		this.data = data;
	}
	
	@ApiModelProperty(value = "리턴 데이터", example = "SampleResponseVo<T>", required = true, hidden = false)
	private T data;
}
