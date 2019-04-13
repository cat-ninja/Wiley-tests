@api @HttpBin
Feature: HttpBin API

  Scenario Outline: Get image from API and compare it with ideal image.
    When <imageExtension> image is received from API
    Then <imageExtension> image should be equal to ideal one

    Examples:
      | imageExtension |
      | PNG            |
      | JPEG           |
      | SVG            |
      | WEBP           |
