package analysis.ui.vo.sample;

import lombok.Data;

@Data
public class ResponseVo<T> {
	private T data;
	public ResponseVo() {}
	public ResponseVo(T data) {
		this.data = data;
	}
}
