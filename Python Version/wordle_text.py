# Name: Cash Belknap
# UTEID: ctb2559
#
# On my honor, <Cash Belknap>, this programming assignment is my own work
# and I have not provided this code to any other student.

import random


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
        if letter in secret_word:
            if letter == secret_word[guess.index(letter)]:
                feedback += 'G'
            else:
                # TODO - implement checking for multiples of same letter
                feedback += 'O'
                i = 0
                for let in guess:
                    if let == letter:
                        i += 1
                # if i > 1:
        else:
            feedback += '-'
    return feedback

if __name__ == '__main__':
    main()
