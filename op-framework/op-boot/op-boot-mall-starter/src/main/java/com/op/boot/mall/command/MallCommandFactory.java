package com.op.boot.mall.command;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.MallResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.op.boot.mall.constants.MallMethod.SEPARATOR;

/**
 * 电商命令工厂类
 *
 * @author chengdr01
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class MallCommandFactory {
    private final Map<String, MallCommand> mallCommandMap;

    public MallCommandFactory(Map<String, MallCommand> mallCommandMap) {
        this.mallCommandMap = mallCommandMap;
    }

    public <T extends MallRequest<?, R>, R extends MallResponse> MallCommand<T, R> getCommand(MallType mallType, String methodName) {
        MallCommand<T, R> mallCommand = mallCommandMap.get(mallType.getValue() + SEPARATOR + methodName);
        if (mallCommand == null) {
            throw new MallException("不支持的电商命令，电商类型【" + mallType.getDesc() + "】，电商方法名【" + methodName + "】");
        }
        return mallCommand;
    }
}
