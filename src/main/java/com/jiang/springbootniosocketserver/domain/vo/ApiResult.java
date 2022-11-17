package com.jiang.springbootniosocketserver.domain.vo;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  geekidea,由spring-boot-plus项目中迁移来的
 * 源地址：https://spring-boot-plus.gitee.io/
 */
@Accessors(chain = true)
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 8004487252556526569L;

    /**
     * 响应码
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 分页数据
     */
    protected long total;
    protected long pageSize;
    protected long pageNum;
    protected long pageCount;

    /**
     * 响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public ApiResult() {
        time = LocalDateTime.now();
    }

    public ApiResult(int code, boolean success, String message, T data, long total, long pageSize, long pageNum, long pageCount, LocalDateTime time) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.pageCount = pageCount;
        this.time = time;
    }

    public static ApiResult<Boolean> result(boolean flag) {
        if (flag) {
            return ok();
        }
        return fail();
    }

    public static ApiResult<Boolean> result(ApiCode apiCode) {
        return result(apiCode, null);
    }

    public static <T> ApiResult<T> result(ApiCode apiCode, T data) {
        return result(apiCode, null, data);
    }

    @SuppressWarnings("unchecked")
    public static <T> ApiResult<T> result(ApiCode apiCode, String message, T data) {
        boolean success = false;
        if (apiCode.getCode() == ApiCode.SUCCESS.getCode()) {
            success = true;
        }
        String apiMessage = apiCode.getMessage();
        if (StringUtils.isNotBlank(apiMessage)) {
            message = apiMessage;
        }
        return (ApiResult<T>) ApiResult.builder()
                .code(apiCode.getCode())
                .message(message)
                .data(data)
                .success(success)
                .time(LocalDateTime.now())
                .build();
    }

    public static ApiResult<Boolean> ok() {
        return ok(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return result(ApiCode.SUCCESS, data);
    }

    /**
     * 分页查询返回封装
     *
     * @param page
     * @return ApiResult
     */
    @SuppressWarnings("unchecked")
    public static <T> ApiResult<T> success(@NotNull Page<T> page) {
        return (ApiResult<T>) ApiResult.builder()
                .code(ApiCode.SUCCESS.getCode())
                .message(ApiCode.SUCCESS.getMessage())
                .data(page.getRecords())
                .success(true)
                .time(LocalDateTime.now())
                .pageCount(page.getPages())
                .pageNum(page.getCurrent())
                .total(page.getTotal())
                .pageSize(page.getSize())
                .build();
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        return result(ApiCode.SUCCESS, message, data);
    }

    public static ApiResult<Map<String, Object>> okMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(key, value);
        return ok(map);
    }

    public static ApiResult<Boolean> fail(ApiCode apiCode) {
        return result(apiCode, null);
    }

    public static ApiResult<String> fail(String message) {
        return result(ApiCode.FAIL, message, null);

    }

    public static <T> ApiResult<T> fail(ApiCode apiCode, T data) {
        if (ApiCode.SUCCESS == apiCode) {
            throw new RuntimeException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
        }
        return result(apiCode, data);

    }

    public static ApiResult<String> fail(Integer errorCode, String message) {
        return new ApiResult<String>()
                .setSuccess(false)
                .setCode(errorCode)
                .setMessage(message);
    }

    public static ApiResult<Map<String, Object>> fail(String key, Object value) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(key, value);
        return result(ApiCode.FAIL, map);
    }

    public static ApiResult<Boolean> fail() {
        return fail(ApiCode.FAIL);
    }

    public static <T> ApiResultBuilder<T> builder() {
        return new ApiResultBuilder<T>();
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public long getTotal() {
        return this.total;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public long getPageNum() {
        return this.pageNum;
    }

    public long getPageCount() {
        return this.pageCount;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public ApiResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public ApiResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ApiResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ApiResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public ApiResult<T> setPageSize(long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ApiResult<T> setPageNum(long pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public ApiResult<T> setPageCount(long pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public ApiResult<T> setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ApiResult)) return false;
        final ApiResult<?> other = (ApiResult<?>) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getCode() != other.getCode()) return false;
        if (this.isSuccess() != other.isSuccess()) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        if (this.getTotal() != other.getTotal()) return false;
        if (this.getPageSize() != other.getPageSize()) return false;
        if (this.getPageNum() != other.getPageNum()) return false;
        if (this.getPageCount() != other.getPageCount()) return false;
        final Object this$time = this.getTime();
        final Object other$time = other.getTime();
        if (this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiResult;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getCode();
        result = result * PRIME + (this.isSuccess() ? 79 : 97);
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        final long $total = this.getTotal();
        result = result * PRIME + (int) ($total >>> 32 ^ $total);
        final long $pageSize = this.getPageSize();
        result = result * PRIME + (int) ($pageSize >>> 32 ^ $pageSize);
        final long $pageNum = this.getPageNum();
        result = result * PRIME + (int) ($pageNum >>> 32 ^ $pageNum);
        final long $pageCount = this.getPageCount();
        result = result * PRIME + (int) ($pageCount >>> 32 ^ $pageCount);
        final Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        return result;
    }

    public String toString() {
        return "ApiResult(code=" + this.getCode() + ", success=" + this.isSuccess() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", total=" + this.getTotal() + ", pageSize=" + this.getPageSize() + ", pageNum=" + this.getPageNum() + ", pageCount=" + this.getPageCount() + ", time=" + this.getTime() + ")";
    }

    public static class ApiResultBuilder<T> {
        private int code;
        private boolean success;
        private String message;
        private T data;
        private long total;
        private long pageSize;
        private long pageNum;
        private long pageCount;
        private LocalDateTime time;

        ApiResultBuilder() {
        }

        public ApiResultBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ApiResultBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResultBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResultBuilder<T> total(long total) {
            this.total = total;
            return this;
        }

        public ApiResultBuilder<T> pageSize(long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public ApiResultBuilder<T> pageNum(long pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public ApiResultBuilder<T> pageCount(long pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public ApiResultBuilder<T> time(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public ApiResult<T> build() {
            return new ApiResult<T>(code, success, message, data, total, pageSize, pageNum, pageCount, time);
        }

        public String toString() {
            return "ApiResult.ApiResultBuilder(code=" + this.code + ", success=" + this.success + ", message=" + this.message + ", data=" + this.data + ", total=" + this.total + ", pageSize=" + this.pageSize + ", pageNum=" + this.pageNum + ", pageCount=" + this.pageCount + ", time=" + this.time + ")";
        }
    }
}
