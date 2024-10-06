import json
import json5
import requests

def fetch_and_save(url):
    try:
        # Fetch the JS file
        response = requests.get(url)
        if response.status_code == 200:
            script = response.text

            parse_js_to_json(script)
        else:
            print(f'Failed to fetch the file. Status code: {response.status_code}')
    except Exception as e:
        print(f'An error occurred: {e}')


# This function basically reads a JS script that seems to be automatically generated so it WILL fail in the future/if
# Carbon website changes.
def parse_js_to_json(content):
    raw_object = content.split("var f=")[1].split(",g=")[0]
    # Using json5 lib to parse the JS object to JSON, as some of the values are not valid JSON
    f_object = json5.loads(raw_object)
    print(f_object)
    with open('color-tokens.json', 'w') as f:
        json.dump(f_object, f, indent=2)
    print(f'Saved JSON to color-tokens.json')

# Retrieval of this URL could be automated by using a web scraper.
url = 'https://carbondesignsystem.com/component---src-pages-elements-color-tokens-mdx-736a6f591a5068a40663.js'
fetch_and_save(url)
