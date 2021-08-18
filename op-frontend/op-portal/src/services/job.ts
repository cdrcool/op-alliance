import request from "../utils/request";
import {Job} from "../models/Job";
import {PageResult} from "../models/PageResult";

/**
 * 保存定时任务
 *
 * @param job 定时任务
 */
export async function saveJob(job: Job) {
    return request.post('/api/op-admin/quartzJob/save', job);
}

/**
 * 删除定时任务
 *
 * @param jobId 任务标识
 */
export async function deleteJob(jobId: number) {
    return request.post(`/api/op-admin/quartzJob/delete?jobId=${jobId}`);
}

/**
 * 批量删除定时任务
 *
 * @param jobIds 任务标识列表
 */
export async function deleteJobs(jobIds: string[]) {
    return request.post(`/api/op-admin/quartzJob/batchDelete`, jobIds);
}

/**
 * 获取定时任务详情
 *
 * @param id 定时任务 id
 */
export async function getJob(id: number): Promise<Job> {
    return request.get(`/api/op-admin/quartzJob/get?id=${id}`);
}

/**
 * 分页查询定时任务列表
 *
 * @param page 当前页索引
 * @param size 页大小
 * @param params 查询参数
 */
export async function queryJobPage(page: number, size: number, params: object): Promise<PageResult<Job>> {
    return request.post('/api/op-admin/quartzJob/queryPage', params);
}

/**
 * 暂停定时任务
 *
 * @param jobId 任务标识
 */
export async function pauseJob(jobId: string) {
    return request.post(`/api/op-admin/quartzJob/pause?jobId=${jobId}`);
}

/**
 * 恢复定时任务
 *
 * @param jobId 任务标识
 */
export async function resumeJob(jobId: string) {
    return request.post(`/api/op-admin/quartzJob/resume?jobId=${jobId}`);
}

/**
 * 触发定时任务
 *
 * @param jobId 任务标识
 */
export async function triggerJob(jobId: string) {
    return request.post(`/api/op-admin/quartzJob/trigger?jobId=${jobId}`);
}
