@api @HttpBin
Feature: HttpBin API

#  Scenario Outline: Get <imageExtension> image from API and compare it with ideal image.
#    When <imageExtension> image is received from API
#    Then <imageExtension> image should be equal to ideal one
#
#    Examples:
#      | imageExtension |
#      | PNG            |
#      | JPEG           |
#      | SVG            |
#      | WEBP           |

  Scenario: Wait for response from API.
    When request is sent with 10 seconds response delay parameter