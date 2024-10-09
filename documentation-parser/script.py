import json
import json5
import requests

def kebab_to_camel_case(kebab_str):
    parts = kebab_str.split('-')
    return (parts[0] + ''.join(word.capitalize() for word in parts[1:])).replace('$', '')

# Recursive function to process the entire JSON (including nested dictionaries)
# Applies kebab case to camel case conversion
# Removes '$' characters
def convert_json(data):
    if isinstance(data, dict):
        new_data = {}
        for key, value in data.items():
            new_key = kebab_to_camel_case(key)
            new_data[new_key] = convert_json(value)
        return new_data
    elif isinstance(data, list):
        return [convert_json(item) for item in data]
    else:
        return data
    
# This function basically reads a JS script that seems to be automatically generated so it WILL fail in the future/if
# Carbon website changes.
def parse_js_to_json(content):
    raw_object = content.split("var f=")[1].split(",g=")[0]
    # Using json5 lib to parse the JS object to JSON, as some of the values are not valid JSON
    return json5.loads(raw_object)

def fetch_and_save(url):
    # Fetch the JS file
    response = requests.get(url)
    if response.status_code == 200:
        script = response.text
        extracted = parse_js_to_json(script)
        converted = convert_json(extracted)
        print(converted)
        with open('src/main/resources/color-tokens.json', 'w') as f:
            json.dump(converted, f, indent=2)
        print(f'Saved JSON to color-tokens.json')
    else:
        print(f'Failed to fetch the file. Status code: {response.status_code}')

# Retrieval of this URL could be automated by using a web scraper.
url = 'https://carbondesignsystem.com/component---src-pages-elements-color-tokens-mdx-736a6f591a5068a40663.js'
fetch_and_save(url)
