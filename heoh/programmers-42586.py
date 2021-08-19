import math

def get_remaining_days(progress, speed):
    return math.ceil((100 - progress) / speed)

def get_job_delays(progresses, speeds):
    return [get_remaining_days(p, s) for p, s in zip (progresses, speeds)]

def pop_next_finished_jobs(delays):
    processed = []
    processed.append(delays.pop(0))

    while delays:
        if processed[0] >= delays[0]:
            processed.append(delays.pop(0))
        else:
            break

    return processed

def solution(progresses, speeds):
    delays = get_job_delays(progresses, speeds)

    deployments = []
    while delays:
        processed = pop_next_finished_jobs(delays)
        deployments.append(len(processed))

    return deployments
