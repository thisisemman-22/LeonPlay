import os
files = {
    'CarPlayHostActivity.kt': [136, 148, 149, 139, 2785, 3074, 3184, 3573],
    'AirPlayPersistence.kt': [81, 82],
    'DiPlaySessionService.kt': [31],
    'DiPlayActivity.kt': [965, 986, 1021],
    'DiagnosticExportStore.kt': [19],
    'CarPlayMediaKeys.kt': [105]
}
base_path = r'C:\Users\emman\Documents\WirelessCarPlay\common\src\main\java\com\shilapi\xcertplay'
for f, lines in files.items():
    path = os.path.join(base_path, f)
    with open(path, 'r', encoding='utf-8') as file:
        content = file.readlines()
    for l in lines:
        print(f'{f}::{l}::{repr(content[l-1])}')
