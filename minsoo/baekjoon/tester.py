import os
import subprocess
import sys
from collections import defaultdict
from typing import Optional
from urllib.parse import urljoin, urlparse

BASE_URL = "https://www.acmicpc.net/problem/"


def check_url_validity(url: str) -> bool:
    try:
        result = urlparse(url)
        return all([result.scheme, result.netloc])
    except ValueError:
        return False


def retrieve_samples(problem: int) -> dict[str, list]:
    if not isinstance(problem, int):
        raise ValueError(f"Problem id must be given in integer not {type(problem)}.")

    if not check_url_validity(url := urljoin(BASE_URL, str(problem))):
        raise ValueError(f"{url} is not a valid url.")

    try:
        import requests
        from bs4 import BeautifulSoup
    except Exception:
        raise ImportError("Exception occurred while importing `requests` or `bs4`.")

    page = requests.get(url)
    soup = BeautifulSoup(page.text, "html.parser")

    samples = defaultdict(list)

    for sample in soup.find_all("pre", attrs="sampledata"):
        _, in_or_out, number = sample.attrs["id"].split("-")
        samples[in_or_out].append(sample.text)

    return samples


def test() -> None:
    filename = os.path.basename(sys.argv[1])
    # force solver and tester to locate in same directory
    filepath = os.path.join(os.path.abspath(os.path.dirname(__file__)), filename)

    numbers = []
    for c in filename:
        if not c.isnumeric():
            break
        numbers.append(c)

    problem = int("".join(numbers))
    samples = retrieve_samples(problem)

    for sample_input, sample_output in zip(samples["input"], samples["output"]):
        with subprocess.Popen(
            ["python", filepath], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.STDOUT
        ) as proc:
            output, err = proc.communicate(input=sample_input.encode("utf-8"))
            message(
                sample_input=sample_input.strip(),
                sample_output=sample_output.strip(),
                stdout=output.decode("utf-8").strip(),
                stderr=err.decode("utf-8").strip() if isinstance(err, bytes) else None
            )


def message(*, sample_input: str, sample_output: str, stdout: str, stderr: Optional[str]) -> None:
    """
    Write any message to be printed.
    """

    print(
        f"sample:\n{sample_input}\n"
        f"\nanswer: {sample_output}"
        f"\noutput: {stdout}\n"
    )


if __name__ == "__main__":
    test()
