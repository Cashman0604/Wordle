# Name: Cash Belknap
# UTEID: ctb2559
#
# On my honor, <Cash Belknap>, this programming assignment is my own work
# and I have not provided this code to any other student.

import random

alphabet = {}


def main():
    """ Plays a text based version of Wordle.
        1. Read in the words that can be choices for the secret word
        and all the valid words. The secret words are a subset of
        the valid words.
        2. Explain the rules to the player.
        3. Get the random seed from the player if they want one.
        4. Play rounds until the player wants to quit.
    """
    secret_words, all_words = get_words()
    welcome_and_instructions()
    continue_playing = True
    while continue_playing:
        guesses = 6
        secret_word = get_secret_word(secret_words)
        global alphabet
        alphabet = make_alphabet()
        output = ''
        while guesses > 0:
            play_round(secret_word, all_words, output)
            guesses -= 1


def play_round(all_words, secret_word, output):
    """ Play one round of Wordle.
        1. Get the secret word.
        2. Initialize the alphabet.
        3. Play the round.
        4. Print the results of the round.
    """
    invalid_guess = True
    while invalid_guess:
        guess = input('\nEnter your guess. A 5 letter word: ').upper()
        if check_valid_word(guess, all_words):
            invalid_guess = False
        else:
            print(guess + ' is not a valid word. Please try again.')
    feedback = compare_guess(guess, secret_word)
    output += feedback + '\n' + guess + '\n'
    print(output)

def welcome_and_instructions():
    """
    Print the instructions and set the initial seed for the random
    number generator based on user input.
    """
    print('Welcome to Wordle.')
    instructions = input('\nEnter y for instructions, anything else to skip: ')
    if instructions == 'y':
        print('\nYou have 6 chances to guess the secret 5 letter word.')
        print('Enter a valid 5 letter word.')
        print('Feedback is given for each letter.')
        print('G indicates the letter is in the word and in the correct spot.')
        print('O indicates the letter is in the word but not that spot.')
        print('- indicates the letter is not in the word.')
    set_seed = input(
        '\nEnter y to set the random seed, anything else to skip: ')
    if set_seed == 'y':
        random.seed(int(input('\nEnter number for initial seed: ')))


def get_words():
    """ Read the words from the dictionary files.
        We assume the two required files are in the current working directory.
        The file with the words that may be picked as the secret words is
        assumed to be names secret_words.txt. The file with the rest of the
        words that are valid user input but will not be picked as the secret
        word are assumed to be in a file named other_valid_words.txt.
        Returns a sorted tuple with the words that can be
        chosen as the secret word and a set with ALL the words,
        including both the ones that can be chosen as the secret word
        combined with other words that are valid user guesses.
    """
    temp_secret_words = []
    with open('secret_words.txt', 'r') as data_file:
        all_lines = data_file.readlines()
        for line in all_lines:
            temp_secret_words.append(line.strip().upper())
    temp_secret_words.sort()
    secret_words = tuple(temp_secret_words)
    all_words = set(secret_words)
    with open('other_valid_words.txt', 'r') as data_file:
        all_lines = data_file.readlines()
        for line in all_lines:
            all_words.add(line.strip().upper())
    return secret_words, all_words


def get_secret_word(secret_words):
    """ Return a random word from the list of secret words.
    """
    return random.choice(secret_words)


def make_alphabet():
    """ Return a tuple of all the letters in the alphabet.
        Each element belongs to the Letter class.
    """
    alphabet = {}
    possible = [True, True, True, True, True]
    green = [False, False, False, False, False]
    for letter in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
        alphabet.update({letter: Letter(letter, possible, green, 0)})
    return alphabet


def check_valid_word(guess, all_words):
    """ Check if the guess is a valid word.
        Return True if the word is valid, False otherwise.
    """
    if len(guess) != 5:
        return False
    for letter in guess:
        if letter not in 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz':
            return False
    if guess in all_words:
        return True
    return False


def compare_guess(guess, secret_word):
    """ Return a string that gives feedback on the guess.
        The string will be 5 characters long.
        Each character will be G, O, or -.
        G indicates the letter is in the word and in the correct spot.
        O indicates the letter is in the word but not that spot.
        - indicates the letter is not in the word.
    """
    feedback = ''
    for letter in guess:
        check_letter(letter, guess.index(letter), secret_word)
        if alphabet[letter].condition == 3:
            if alphabet[letter].green_spaces[guess.index(letter)]:
                feedback += 'G'
            elif alphabet[letter].possible_spots[guess.index(letter)]:
                feedback += 'O'
            else:
                feedback += '-'
        elif alphabet[letter].condition == 2:
            feedback += 'O'
        else:
            feedback += '-'
    return feedback


def check_letter(let, index, secret_word):
    if let in secret_word:
        if let == secret_word[index]:
            alphabet[let].condition = 3
            alphabet[let].set_possible_spot(index, True)
            alphabet[let].set_green_space(index)
            i = 0
            for green in alphabet[let].green_spaces:
                if green:
                    i += 1
            j = 0
            for letter in secret_word:
                if letter == let:
                    j += 1
            if i == j:
                alphabet[let].possible_spots = alphabet[let].green_spaces
        else:
            alphabet[let].condition = 2
            alphabet[let].set_possible_spot(index, False)
    else:
        alphabet[let].condition = 1
        alphabet[let].possible_spots = [False, False, False, False, False]


if __name__ == '__main__':
    main()


class Letter:
    letter = ''
    possible_spots = []
    green_spaces = []
    # condition of the letter 
    # (0 = not used, 1 = eliminated, 2 = used, 3 = used and green)
    condition = 0 

    def __init__(self, letter, possible_spots, green_spaces, condition):
        self.letter = letter
        self.possible_spots = possible_spots
        self.green_spaces = green_spaces
        self.condition = condition
    

    # getters and setters
    def get_letter(self):
        return self.letter
    

    def get_possible_spots(self):
        return self.possible_spots
    

    def valid_spot(self, x):
        return self.possible_spots[x]
    

    def set_possible_spot(self, x, bool):
        self.possible_spots[x] = bool
    

    def get_green_spaces(self):
        return self.green_spaces
    

    def get_one_green_space(self, x):
        return self.green_spaces[x]
    

    def set_green_space(self, x):
        self.green_spaces[x] = True
    

    def get_condition(self):
        return self.condition
    

    def set_condition(self, x):
        self.condition = x