@api @HttpBin
Feature: HttpBin API

  Scenario Outline: Get <imageExtension> image from API and compare it with ideal image.
    When <imageExtension> image is received from API
    Then <imageExtension> image should be equal to ideal one

    Examples:
      | imageExtension |
      | PNG            |
      | JPEG           |
      | SVG            |
      | WEBP           |

  Scenario Outline: Wait <delayTime> seconds for response from API.
    When request is sent with <delayTime> seconds response delay parameter
    Then <delayTime> seconds delay should be present in response body
    Examples:
      | delayTime |
      | 1         |
      | 5         |
      | 10        |
